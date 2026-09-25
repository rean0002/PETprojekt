package controllers;

import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
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

    }

    public static void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        //System.out.println("Login forsøg med email='" + email + "' password='" + password + "'");

        try {
            String date = dateservice.getDate();
            ctx.attribute("date", date);
            User user = userService.login(email, password);
            ctx.sessionAttribute("user", user);
            ctx.render("templates/dashboard.html");
        }catch(IllegalUserDataException e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    }

    public static void renderDashboard(Context ctx) {

        String toggle=ctx.queryParam("view");
        if(toggle.equals("alle")){
            HouseholdController.loadHouseholdDashboard(ctx);
         }else {
            String date = dateservice.getDate();
            ctx.attribute("date", date);
            ctx.render("templates/dashboard.html");}
        }




    public static void createUser(Context ctx){

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try{
            userService.createUser(firstName, lastName, email, password);
            ctx.redirect("/index.html");
        }catch (IllegalUserDataException e){
            ctx.status(400);
            ctx.result(e.getMessage());
        }

    }



}
