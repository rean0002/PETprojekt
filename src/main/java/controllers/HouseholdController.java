package controllers;

import Exceptions.IllegalTaskDataException;
import entities.User;
import Exceptions.IllegalUserDataException;
import entities.Household;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import mappers.ConnectionPool;
import services.UtilService;

import static controllers.UserController.*;

public class HouseholdController {
    private ConnectionPool connectionPool;

    public HouseholdController(ConnectionPool connectionpool){
        this.connectionPool = connectionPool;
    }


    public void setRoutes(JavalinConfig config){
        config.routes.get("/household-choice", ctx -> {ctx.render("templates/household-choice.html");});

        config.routes.get("/create-household", ctx -> ctx.render("public/create-household.html"));
        config.routes.post("/create-household", ctx -> createHousehold(ctx));

        config.routes.get("/tilknyt-household", ctx -> renderHouseholdchoice(ctx));
        config.routes.post("/tilknyt-household", ctx -> tilknytHousehold(ctx));

       // config.routes.post("/household-dashboard", ctx -> renderDashboard(ctx));
    }

    public void createHousehold(Context ctx) throws IllegalUserDataException {
        String navn = ctx.formParam("navn");
        String medlemmer = ctx.formParam("medlemmer");
        Household household = new Household(UtilService.capitalizeFirst(navn));
        ctx.sessionAttribute("household", household);

        User user = ctx.sessionAttribute("user");
        if (user == null){
            throw new IllegalUserDataException("Kunne ikke finde bruger");
        }
        user.setHousehold(household);
        household.addMember(user);

        ctx.redirect("/dashboard.html");
    }

    public void renderHouseholdchoice(Context ctx){

        User user = ctx.sessionAttribute("user");

        ctx.render("public/tilknyt-household.html");
    }



    public void tilknytHousehold(Context ctx){
        String kode = ctx.formParam("kode");

        ctx.redirect("/dashboard.html");
    }

}
