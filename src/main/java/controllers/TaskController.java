package controllers;

import io.javalin.config.JavalinConfig;
import services.TaskService;

public class TaskController {

    static TaskService taskService = new TaskService();

    public static void setRoutes(JavalinConfig config){

    }
}

