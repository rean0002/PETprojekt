package services;

import Exceptions.IllegalTaskDataException;
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

    public Task createTask(String title, String description, User user, String categoryStr, String frequencyStr) throws IllegalTaskDataException {

        if (title == null || title.isBlank()){
            throw new IllegalTaskDataException("Udfyld venligst en titel");
        }
        if (categoryStr == null || categoryStr.isBlank()){
            throw new IllegalTaskDataException("Vælg venligst en kategori");
        }

        TaskCategory category;
        try {
            category = TaskCategory.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalTaskDataException("Ugyldig kategori valgt");
        }

        TaskFrequency frequency = TaskFrequency.valueOf(frequencyStr);

        Task task = new Task(nextId++, title, description, user, category, frequency);
        user.addTask(task);

        return task;
    }
}
