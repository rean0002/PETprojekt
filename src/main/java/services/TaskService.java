package services;

import entities.Task;
import entities.User;
import entities.TaskCategory;
import entities.TaskFrequency;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    static List<Task> tasks = new ArrayList<>();
    static int nextId = 1;

    public void addTask(String title, String description, User user, TaskCategory category, TaskFrequency frequency) {

        Task task = new Task( nextId++, title, description, user, category, frequency);

        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
