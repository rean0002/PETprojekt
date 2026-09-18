package controllers;

import entities.User;
import factories.UserFactory;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    //static ArrayList<User> users=new ArrayList<>(); //skal selvfølgelig flyttes

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
            ctx.redirect("/create-user.html?error=Password skal være mellem 8 og 15 tegn");
            return;
        }

        userService.addUser(new User(email, password, firstName, lastName));

        ctx.redirect("/index.html");

        System.out.println(userService.getUser(email));
    }


}
