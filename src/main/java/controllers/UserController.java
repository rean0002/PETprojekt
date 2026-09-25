package controllers;

import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.UserService;



public class UserController {

    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUser(ctx));

        config.routes.get("/dashboard", ctx -> ctx.render("/dashboard.html"));
    }

    public static void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        System.out.println("Login forsøg med email='" + email + "' password='" + password + "'");

        try {
            User user = userService.login(email, password);
            ctx.sessionAttribute("user", user);
            ctx.render("templates/dashboard.html");
        }catch(IllegalUserDataException e){
            ctx.result(e.getMessage());
            ctx.status(404);
        }
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
