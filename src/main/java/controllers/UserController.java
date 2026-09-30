package controllers;

import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.DateService;
import services.UserService;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserController {

    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();
    static DateService dateservice = new DateService();

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
            ctx.status(404);
        }
    }

    public static void renderDashboard(Context ctx) {
        String date = dateservice.getDate();
        ctx.attribute("date", date);

        boolean husstand = "alle".equals(ctx.queryParam("view"));
        ctx.attribute("husstand", husstand);

        if(husstand){
            User user= ctx.sessionAttribute("user");
            ctx.attribute("firstName", user.getHousehold().getName());
            ctx.attribute("tasks", user.getHousehold().getTasks());
        } else {
            User user= ctx.sessionAttribute("user");
            ctx.attribute("firstName", user.getFirstName());
            ctx.attribute("tasks", user.getTasks());
        }
        ctx.render("templates/dashboard.html");


        }

    private static void markTaskAsDone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User user = ctx.sessionAttribute("user");
        user.findTask(id).setDone(true);
        ctx.status(204);
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
            ctx.status(400);
            ctx.result(e.getMessage());
        }

    }

}