package controllers;

import Exceptions.IllegalUserDataException;
import entities.Household;
import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

import static controllers.UserController.dateservice;
import static controllers.UserController.userService;

public class HouseholdController {


    public static void setRoutes(JavalinConfig config){
        config.routes.get("/household-dashboard", ctx -> loadHouseholdDashboard(ctx) );

    }




    public static void loadHouseholdDashboard (Context ctx){

        try {
            String date = dateservice.getDate();
            ctx.attribute("date", date);
            User user = ctx.sessionAttribute("user");
            ctx.sessionAttribute("household", user.getHousehold());

            ctx.render("templates/household-dashboard.html");
        }catch(Exception e){  //OBS EXCEPTION HANDLING ER FOR ABSTRAKT
            ctx.result(e.getMessage());
            ctx.status(404);
        }

    }



}
