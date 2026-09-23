package controllers;

import entities.TaskCategory;
import entities.TaskFrequency;
import entities.User;
import factories.TaskFactory;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.TaskService;
import services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    //static ArrayList<User> users=new ArrayList<>(); //skal selvfølgelig flyttes

    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();
    static TaskService taskService = new TaskService();
    static TaskFactory taskFactory = new TaskFactory();


    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUserProfile(ctx));

        config.routes.get("/opret-opgave", ctx -> ctx.redirect("/opret-opgave.html"));
        config.routes.post("/opret-opgave", ctx -> opretOpgave(ctx));

        config.routes.before("/dashboard.html", ctx -> {
            User user = ctx.sessionAttribute("user");
            if(user == null){
                ctx.redirect("/index.html");
            }
        });

        config.routes.get("/dashboard.html", ctx -> {
            User user = ctx.sessionAttribute("user");
            ctx.render("templates/dashboard.html", Map.of("fornavn", user.getFirstName()));
        });


    }

    public static void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        System.out.println("Login forsøg med email='" + email + "' password='" + password + "'");

        User user = userService.login(email, password);

        if(user != null){
            ctx.sessionAttribute("user", user);
            ctx.redirect("/dashboard.html");
        } else {
            ctx.status(404);
            ctx.result("Brugeren findes ikke");
        }
    }

    public static void createUserProfile(Context ctx){
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (!userService.validatePassword(password)) {
            ctx.status(400);
            ctx.result("Password skal være mellem 8 og 15 tegn");
            return;
        }

        userService.addUser(new User(email, password, firstName, lastName));
        ctx.redirect("/index.html");
        System.out.println(userService.getUser(email));
    }

    public static void opretOpgave(Context ctx) {

        String title = ctx.formParam("titel");
        String description = ctx.formParam("beskrivelse");
        String categoryString = ctx.formParam("taskCategory");   // hidden input
        String dateString = ctx.formParam("dato");
        String timeString = ctx.formParam("tidspunkt");
        String frequencyString = ctx.formParam("gentages");
        String ansvarlig = ctx.formParam("ansvarlig");
        String videregives = ctx.formParam("vidergives"); // checkbox

        User user = null;
        for (User u : UserFactory.getUsers()) {
            if (u.getFirstName().equalsIgnoreCase(ansvarlig)) {
                user = u;
                break;
            }
        }

        TaskCategory category = TaskCategory.valueOf(categoryString.toUpperCase());
        TaskFrequency frequency = TaskFrequency.valueOf(frequencyString.toUpperCase());

        taskService.addTask(title, description, user, category, frequency);

        ctx.redirect("/dashboard.html");
    }


}
