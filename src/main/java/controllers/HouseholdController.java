package controllers;

import entities.Household;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class HouseholdController {


    public static void setRoutes(JavalinConfig config){
        config.routes.post("/household-dashboard", ctx -> loadHouseholdDashboard(ctx) );

    }




    public static void loadHouseholdDashboard (Context ctx){







    }



}
