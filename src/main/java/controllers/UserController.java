package controllers;

import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {
    //static ArrayList<User> users=new ArrayList<>(); //skal selvfølgelig flyttes

    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUser(ctx));

        config.routes.get("/dashboard", ctx -> ctx.render("templates/dashboard.html"));
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

   /* public static void createUserProfile(Context ctx){
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
    }*/


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



    /*public static void loadDashboard (Context ctx){

        User user = ctx.sessionAttribute("user");
        if(user != null) {
            ctx.render("templates/dashboard.html", Map.of("user", user));
        } else ctx.redirect("/index.html");
}*/

}
