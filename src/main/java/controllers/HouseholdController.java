package controllers;

import entities.User;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

import java.util.Map;

public class HouseholdController {

    public static void setRoutes(JavalinConfig config){
        config.routes.get("/household-choice", ctx -> {
            User user = ctx.sessionAttribute("user");
            ctx.render("templates/household-choice.html", Map.of("fornavn", user.getFirstName()));
        });

        config.routes.get("/create-household", ctx -> ctx.render("public/create-household.html"));
        config.routes.post("/create-household", ctx -> createHousehold(ctx));

        config.routes.get("/tilknyt-household", ctx -> ctx.render("public/tilknyt-household.html"));
        config.routes.post("/tilknyt-household", ctx -> tilknytHousehold(ctx));

        config.routes.post("/household-dashboard", ctx -> loadHouseholdDashboard(ctx));
    }

    public static void createHousehold(Context ctx){
        String navn = ctx.formParam("navn");
        String medlemmer = ctx.formParam("medlemmer");



        ctx.redirect("/dashboard");
    }

    public static void tilknytHousehold(Context ctx){
        String kode = ctx.formParam("kode");



        ctx.redirect("/dashboard");
    }

    public static void loadHouseholdDashboard (Context ctx){


    }

}