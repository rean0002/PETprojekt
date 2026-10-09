package controllers;

import Exceptions.DatabaseException;
import Exceptions.IllegalUserDataException;
import entities.Task;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import mappers.ConnectionPool;
import mappers.UserMapper;
import mappers.HouseholdMapper;
import org.jetbrains.annotations.NotNull;
import services.DateService;
import services.TaskService;
import services.UserService;
import entities.Task;
import java.util.List;
import java.util.ArrayList;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class UserController {
    private ConnectionPool connectionPool;
    private UserService userService;
    private DateService dateservice;
    private TaskService taskService;
    private HouseholdMapper householdMapper;

    public UserController (ConnectionPool connectionpool){

        this.userService=new UserService(new UserMapper());
        this.dateservice=new DateService();
        this.taskService=new TaskService();
        this.householdMapper= new HouseholdMapper();
    }



    public void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUser(ctx));

        config.routes.get("/dashboard", ctx -> renderDashboard(ctx));

        config.routes.post("/tasks/{id}/done", ctx -> markTaskAsDone(ctx));

    }


    public void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = userService.login(email, password);
            ctx.sessionAttribute("user", user);
            renderDashboard(ctx);
        }catch(IllegalUserDataException e){
            ctx.result(e.getMessage());
            ctx.status(400);
        }
    }

    public void renderDashboard(Context ctx) {

        boolean husstand = "alle".equals(ctx.queryParam("view"));
        ctx.attribute("husstand", husstand); //det afgøres om vi er på husstand eller users dashboard, ved at kigge på url view, hvis view=alle vil boolean være true

        LocalDate selectedDate = resolveSelectedDate(ctx);
        setDateAttributes(ctx, selectedDate);

        User user= ctx.sessionAttribute("user");
        setTaskAttributes(ctx, user, selectedDate, husstand);

        ctx.render("templates/dashboard.html");

        }

    private void setTaskAttributes(Context ctx, User user, LocalDate selectedDate, boolean husstand) {

        List <Task> filteredBydate=null;
        List <Task> filteredByUser=null;

        try{
        filteredBydate = filterTasksByDate(householdMapper.tasks(user.getHousehold()), selectedDate);
        filteredByUser = filterTaskByUser(filteredBydate, user);
       }catch (DatabaseException d){
           d.getMessage();
       }


        if(husstand){
            ctx.attribute("firstName", user.getHousehold().getName());
            ctx.attribute("tasks", filteredBydate);
        } else {
            ctx.attribute("firstName", user.getFirstName());
            ctx.attribute("tasks", filteredByUser);
        }

    }

    private void setDateAttributes(Context ctx, LocalDate selectedDate) {
        String date = dateservice.getDate(selectedDate); //selectedDate formatteres til "EEEE dd. MMMM"
        ctx.attribute("date", date);
        List<LocalDate> next7Days = dateservice.getNext7Days(); //laves altid på dagens dato
        ctx.attribute("next7Days", next7Days);
        ctx.attribute("selectedDate", selectedDate);

        List<String> next7DayLabels = new ArrayList<>();
        for (LocalDate day : next7Days) {
            next7DayLabels.add(dateservice.getDate(day));
        }
        ctx.attribute("next7DayLabels", next7DayLabels);

    }

    private LocalDate resolveSelectedDate(Context ctx) {

        String dateParam = ctx.queryParam("date"); //hvilken dato står i url?
        if (dateParam == null){ //hvis der ikke er en dato i url, sæt dato til i dag
           return LocalDate.now();
        }else {
            return LocalDate.parse(dateParam); //ellers skal url dato parses
        }
    }

    private List<Task> filterTasksByDate(List<Task> tasks, LocalDate date){
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDate()!= null && task.getDate().equals(date)) {
                filtered.add(task);
            }
        }
        return filtered;
    }


    private List<Task> filterTaskByUser (List<Task> tasks, User user){
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getResponsibleUser() != null && task.getResponsibleUser().equals(user)) {
                filtered.add(task);
            }
        }
        return filtered;
    }


    private void markTaskAsDone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User user = ctx.sessionAttribute("user");
        ArrayList <Task> tasks=null;

        try {
            tasks =householdMapper.tasks(user.getHousehold());
        }catch (DatabaseException d){
            d.getMessage();
        }

        for(Task t:tasks){
            if (t.getId()==id){
                t.markAsDone();

                String tidspunkt = t.getCompletedAt()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

                ctx.json(Map.of("completedAt", tidspunkt));
            }
        }




    }


    public void createUser(Context ctx){

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try{
            User user = userService.createUser(firstName, lastName, email, password);
            ctx.sessionAttribute("user", user);
            ctx.redirect("/household-choice");
        }catch (IllegalUserDataException e){
            ctx.redirect("/create-user.html?error="+ java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }

    }

}