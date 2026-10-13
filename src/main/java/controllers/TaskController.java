package controllers;

import Exceptions.IllegalTaskDataException;
import Exceptions.IllegalUserDataException;
import entities.Household;
import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import mappers.ConnectionPool;
import mappers.TaskMapper;
import mappers.UserMapper;
import services.TaskService;
import services.UserService;

import java.util.ArrayList;


public class TaskController {
    private ConnectionPool connectionPool;
    private TaskService taskService;
    private UserService userService;


    public TaskController(ConnectionPool connectionpool){
        this.connectionPool = connectionPool;
        this.taskService=new TaskService(new TaskMapper());
        this.userService=new UserService(new UserMapper());
    }



    public void setRoutes(JavalinConfig config){
        config.routes.post("/opret-opgave", ctx -> opretOpgave(ctx) );
        config.routes.get("/opret-opgave", ctx -> visOpretOpgave(ctx));
    }

    private void visOpretOpgave(Context ctx) {
        User user = ctx.sessionAttribute("user");
        Household household = ctx.sessionAttribute("household");

        household.setMembers(userService.addMembersToHousehold(household.getId()));
        ctx.attribute("household", household);

        ctx.render("/templates/opret-opgave.html");
    }


    public void opretOpgave(Context ctx) throws IllegalTaskDataException, IllegalUserDataException{
        String title = ctx.formParam("titel");
        String beskrivelse = ctx.formParam("beskrivelse");
        String taskCategory = ctx.formParam("taskCategory");
        String gentages = ctx.formParam("gentages");
        String ansvarlig = ctx.formParam("ansvarlig");
        String dato = ctx.formParam("dato");
        String tidspunkt = ctx.formParam("tidspunkt");

        User user= ctx.sessionAttribute("user");
        Household household=ctx.sessionAttribute("household");


        User ansvarligBruger = userService.getUserByFirstNameAndHouseholdId(household.getId(), ansvarlig);


        try{
            taskService.createTask(title, beskrivelse, ansvarligBruger, taskCategory, gentages, dato, tidspunkt, household);
            ctx.redirect("/dashboard");
        }catch (IllegalTaskDataException e){
            ctx.status(400);
            ctx.result(e.getMessage());
        }
    }


}

