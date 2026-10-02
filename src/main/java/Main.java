import Exceptions.IllegalTaskDataException;
import Exceptions.IllegalUserDataException;
import controllers.HouseholdController;
import controllers.TaskController;
import controllers.UserController;
import factories.UserFactory;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    public static void main(String[] args){
        UserFactory.createUsers();

        var app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            UserController.setRoutes(config);
            TaskController.setRoutes(config);
            HouseholdController.setRoutes(config);
            config.routes.exception(IllegalUserDataException.class, (exception, ctx) -> ctx.status(400).result(exception.getMessage()));
            config.routes.exception(IllegalTaskDataException.class, (exception, ctx) -> ctx.status(400).result(exception.getMessage()));
            config.fileRenderer(new JavalinThymeleaf());
        }).start(7070);}
}
