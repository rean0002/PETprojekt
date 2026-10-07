package controllers;

import Exceptions.IllegalUserDataException;
import entities.Task;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
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

    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();
    static DateService dateservice = new DateService();
    static TaskService taskService = new TaskService();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUser(ctx));

        config.routes.get("/dashboard", ctx -> renderDashboard(ctx));

        config.routes.post("/tasks/{id}/done", ctx -> markTaskAsDone(ctx));

    }


    public static void login(Context ctx){
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

    public static void renderDashboard(Context ctx) {

        boolean husstand = "alle".equals(ctx.queryParam("view"));
        ctx.attribute("husstand", husstand);
        String dateParam = ctx.queryParam("date");
        LocalDate selectedDate;

        if (dateParam == null){
            selectedDate = LocalDate.now();
        }else {
            selectedDate = LocalDate.parse(dateParam);
        }
        String date = dateservice.getDate(selectedDate);
        ctx.attribute("date", date);
        List<LocalDate> next7Days = dateservice.getNext7Days();
        ctx.attribute("next7Days", next7Days);
        ctx.attribute("selectedDate", selectedDate);

        List<String> next7DayLabels = new ArrayList<>();
        for (LocalDate day : next7Days) {
            next7DayLabels.add(dateservice.getDate(day));
        }
        ctx.attribute("next7DayLabels", next7DayLabels);


        if(husstand){
            User user= ctx.sessionAttribute("user");
            ctx.attribute("firstName", user.getHousehold().getName());
            ctx.attribute("tasks", filterTasksByDate(new ArrayList<>(user.getHousehold().getTasks()), selectedDate));
        } else {
            User user= ctx.sessionAttribute("user");
            ctx.attribute("firstName", user.getFirstName());
            ctx.attribute("tasks", filterTasksByDate(user.getTasks(), selectedDate));
        }
        ctx.render("templates/dashboard.html");

        }

    private static List<Task> filterTasksByDate(List<Task> tasks, LocalDate date){
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDate()!= null && task.getDate().equals(date)) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    private static void markTaskAsDone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User user = ctx.sessionAttribute("user");
        Task task=taskService.findTask(id, user.getHousehold());
        task.setDone(true);

        String tidspunkt = task.getCompletedAt()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        ctx.json(Map.of("completedAt", tidspunkt));

    }


    public static void createUser(Context ctx){

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