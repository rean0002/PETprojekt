package controllers;

import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.UserService;

import java.util.ArrayList;

public class UserController {
    static ArrayList<User> users=new ArrayList<>(); //skal selvfølgelig flyttes
    static UserService userService = new UserService();
    static UserFactory userFactory = new UserFactory();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

        config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
        config.routes.post("/create-user", ctx -> createUserProfile(ctx));

        config.routes.before("/dashboard.html", ctx -> {
            User user = ctx.sessionAttribute("user");
            if(user == null){
                ctx.redirect("/index.html");
            }
        });
    }

    public static void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

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
        String firstName=ctx.formParam("firstname");
        String lastname=ctx.formParam("lastname");
        String email=ctx.formParam("email");
        String passsword=ctx.formParam("password");

        users.add(new User(email, passsword, firstName, lastname));

        ArrayList <User> users=userFactory.createUser();

        ctx.redirect("/index.html");

        System.out.println(users);
    }


}
