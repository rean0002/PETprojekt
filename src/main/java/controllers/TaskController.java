package controllers;

import Exceptions.IllegalTaskDataException;
import Exceptions.IllegalUserDataException;
import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.TaskService;

import static controllers.UserController.createUser;
import static controllers.UserController.userService;


public class TaskController {

    static TaskService taskService = new TaskService();

    public static void setRoutes(JavalinConfig config){
        config.routes.post("/opret-opgave", ctx -> opretOpgave(ctx) );
        config.routes.get("/opret-opgave", ctx -> visOpretOpgave(ctx));
    }

    private static void visOpretOpgave(Context ctx) {
        User user = ctx.sessionAttribute("user");
        ctx.attribute("household", user.getHousehold());
        ctx.render("/templates/opret-opgave.html");
    }


    public static void opretOpgave(Context ctx) throws IllegalUserDataException {
        String title = ctx.formParam("titel");
        String beskrivelse = ctx.formParam("beskrivelse");
        String taskCategory = ctx.formParam("taskCategory");
        String gentages = ctx.formParam("gentages");

        User user= ctx.sessionAttribute("user");

        User ansvarlig = userService.getUserByFirstName(ctx.formParam("ansvarlig"));


        try{
            taskService.createTask(title, beskrivelse, ansvarlig, taskCategory, gentages);
            UserController.renderDashboard(ctx);
        }catch (IllegalTaskDataException e){
            ctx.status(400);
            ctx.result(e.getMessage());
        }
    }


}

