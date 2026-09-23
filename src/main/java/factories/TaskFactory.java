package factories;

import entities.Task;
import entities.User;
import entities.TaskCategory;
import entities.TaskFrequency;

import java.util.ArrayList;
import java.util.List;

public class TaskFactory {

    static List<Task> tasks = new ArrayList<>();
    static int nextId = 1;

    public static List<Task> createTasks(List<User> users) {

        tasks.add(new Task(
                nextId++,
                "Rengør køkken",
                "Tør bordene af",
                users.get(0),
                TaskCategory.HOME,
                TaskFrequency.DAILY
        ));

        tasks.add(new Task(
                nextId++,
                "Vask tøj",
                "Vask alt farvet tøj på 40 grader",
                users.get(1),
                TaskCategory.CLEANING,
                TaskFrequency.WEEKLY
        ));

        return tasks;
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static List<Task> getTasks() {
        return tasks;
    }
}
