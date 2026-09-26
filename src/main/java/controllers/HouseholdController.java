package controllers;

import entities.User;
import Exceptions.IllegalUserDataException;
import entities.Household;
import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

import java.util.Map;

import static controllers.UserController.dateservice;
import static controllers.UserController.userService;

public class HouseholdController {


    public static void setRoutes(JavalinConfig config){
        config.routes.get("/household-choice", ctx -> {
            User user1 = ctx.sessionAttribute("user1");
            ctx.render("templates/household-choice.html", Map.of("fornavn", user1.getFirstName()));
        });

        config.routes.get("/create-household", ctx -> ctx.render("public/create-household.html"));
        config.routes.post("/create-household", ctx -> createHousehold(ctx));

        config.routes.get("/tilknyt-household", ctx -> ctx.render("public/tilknyt-household.html"));
        config.routes.post("/tilknyt-household", ctx -> tilknytHousehold(ctx));

        config.routes.post("/household-dashboard", ctx -> loadHouseholdDashboard(ctx));
        config.routes.get("/household-dashboard", ctx -> loadHouseholdDashboard(ctx));
    }

    public static void createHousehold(Context ctx){
        String navn = ctx.formParam("navn");
        String medlemmer = ctx.formParam("medlemmer");




        UserController.renderDashboard(ctx);
    }

    public static void tilknytHousehold(Context ctx){
        String kode = ctx.formParam("kode");



        UserController.renderDashboard(ctx);
    }

    public static void loadHouseholdDashboard (Context ctx){

        try {
            String date = dateservice.getDate();
            ctx.attribute("date", date);
            User user = ctx.sessionAttribute("user");

           //Household household = user.getHousehold();
            //ctx.sessionAttribute("household", household);

            ctx.render("templates/household-dashboard.html");
        }catch(Exception e){  //OBS EXCEPTION HANDLING ER FOR ABSTRAKT
            ctx.result(e.getMessage());
            ctx.status(404);
        }

    }



}
