import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.ArrayList;

public class Main {

    static ArrayList <User> users=new ArrayList<>(); //skal selvfølgelig flyttes

    public static void main (String[] args){

        var app = Javalin.create(config ->{

           // userControllers.setRoutes(config);

            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf());

            config.routes.get("/create-user", ctx -> ctx.redirect("/create-user.html"));
            config.routes.post("/create-user", ctx -> createUser(ctx));





        }).start(7070);



    }

    public static void createUser(Context ctx){
        String firstName=ctx.formParam("firstname");
        String lastname=ctx.formParam("lastname");
        String email=ctx.formParam("email");
        String passsword=ctx.formParam("password");

        users.add(new User(email, passsword, firstName, lastname));

        ctx.redirect("/success.html");

        System.out.println(users);
    }





}
