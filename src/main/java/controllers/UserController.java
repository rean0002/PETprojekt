package controllers;

import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.UserService;

public class UserController {
    static UserService userService = new UserService();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/login", ctx -> login(ctx));
        config.routes.get("/login", ctx -> ctx.redirect("/index.html"));

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
}
