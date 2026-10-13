package controllers;

import Exceptions.DatabaseException;
import entities.User;
import Exceptions.IllegalUserDataException;
import entities.Household;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import mappers.ConnectionPool;
import mappers.HouseholdMapper;
import mappers.UserMapper;
import services.HouseholdService;
import services.UserService;
import services.UtilService;


public class HouseholdController {
    private ConnectionPool connectionPool;
    private UserService userService;
    private HouseholdMapper householdMapper;
    private HouseholdService householdService;

    public HouseholdController(ConnectionPool connectionpool){
        this.connectionPool = connectionPool;
        this.userService=new UserService(new UserMapper());
        this.householdService=new HouseholdService(new HouseholdMapper());
        this.householdMapper=new HouseholdMapper();
    }


    public void setRoutes(JavalinConfig config){
        config.routes.get("/household-choice", ctx -> {ctx.render("templates/household-choice.html");});

        config.routes.get("/create-household", ctx -> ctx.render("public/create-household.html"));
        config.routes.post("/create-household", ctx -> createHousehold(ctx));

        config.routes.get("/tilknyt-household", ctx -> renderHouseholdchoice(ctx));
        config.routes.post("/tilknyt-household", ctx -> tilknytHousehold(ctx));


    }

    public void createHousehold(Context ctx) throws IllegalUserDataException {
        String navn = ctx.formParam("navn");
        String medlemmer = ctx.formParam("medlemmer");

        User user = ctx.sessionAttribute("user");
        if (user == null){
            throw new IllegalUserDataException("Kunne ikke finde bruger");
        }

        Household household = householdService.createHousehold(UtilService.capitalizeFirst(navn));

        try {
            householdMapper.createHousehold(household);
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Husstanden kunne ikke oprettes");
        }

        userService.makeAdmin(user.getId(), household.getId());

        ctx.sessionAttribute("household", household);

        household.addMember(user);

        ctx.redirect("/dashboard");
    }

    public void renderHouseholdchoice(Context ctx){

        User user = ctx.sessionAttribute("user");

        ctx.render("public/tilknyt-household.html");
    }


    public void tilknytHousehold(Context ctx) throws IllegalUserDataException {
        String kode = ctx.formParam("kode");
        User user = ctx.sessionAttribute("user");
        if (user == null){
            throw new IllegalUserDataException("Kunne ikke tilknytte husholdning");
        }

        userService.joinHousehold(user.getId(), kode);

        try {
            Household household = householdMapper.getHouseholdByCode(kode);
            ctx.sessionAttribute("household", household);
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Husstanden kunne ikke findes");
        }

        ctx.redirect("/dashboard");
    }

}
