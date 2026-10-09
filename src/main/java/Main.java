import Exceptions.IllegalTaskDataException;
import Exceptions.IllegalUserDataException;
import controllers.HouseholdController;
import controllers.TaskController;
import controllers.UserController;
import factories.UserFactory;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import mappers.ConnectionPool;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "petproject";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args){
        UserFactory.currentHousehold();


        var app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            UserController userController = new UserController(connectionPool);
            TaskController taskController = new TaskController(connectionPool);
            HouseholdController householdController = new HouseholdController(connectionPool);

            userController.setRoutes(config);
            taskController.setRoutes(config);
            householdController.setRoutes(config);

            config.routes.exception(IllegalUserDataException.class, (exception, ctx) -> ctx.status(400).result(exception.getMessage()));
            config.routes.exception(IllegalTaskDataException.class, (exception, ctx) -> ctx.status(400).result(exception.getMessage()));
            config.fileRenderer(new JavalinThymeleaf());
        }).start(7070);


    }
}
