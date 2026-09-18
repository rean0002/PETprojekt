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
            config.fileRenderer(new JavalinThymeleaf());
        }).start(7070);
    }
}