package controllers;

import Exceptions.IllegalTaskDataException;
import Exceptions.IllegalUserDataException;
import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import services.TaskService;

import static controllers.UserController.userService;


public class TaskController {

    static TaskService taskService = new TaskService();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/opret-opgave", ctx -> opretOpgave(ctx) );
        config.routes.get("/opret-opgave", ctx -> ctx.redirect("templates/dashboard.html"));
    }


    public static void opretOpgave(Context ctx){
        String title = ctx.formParam("titel");
        String beskrivelse = ctx.formParam("beskrivelse");
        String taskCategory = ctx.formParam("taskCategory");
        String ansvarlig = ctx.formParam("ansvarlig");
        String gentages = ctx.formParam("gentages");

        User ansvarligBruger = userService.getUserByFirstName(ansvarlig);

        try{
            taskService.createTask(title, beskrivelse, ansvarligBruger, taskCategory, gentages);
            ctx.redirect("/dashboard");
        }catch (IllegalTaskDataException e){
            ctx.status(400);
            ctx.result(e.getMessage());
        }
    }


}

