package services;

import Exceptions.IllegalTaskDataException;
import entities.Task;
import entities.User;
import entities.TaskCategory;
import entities.TaskFrequency;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    static int nextId = 6;

    public Task createTask(String title, String description, User user, String categoryStr, String frequencyStr) throws IllegalTaskDataException {

        if (title == null || title.isBlank()){
            throw new IllegalTaskDataException("Udfyld venligst en titel");
        }

        if (user == null){
            throw new IllegalTaskDataException("Brugeren findes ikke");
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

        if (frequencyStr == null || frequencyStr.isBlank()){
            throw new IllegalTaskDataException("Vælg venligst en frekvens");
        }
        TaskFrequency frequency;
        try {
            frequency = TaskFrequency.valueOf(frequencyStr);
        } catch (IllegalArgumentException e){
            throw new IllegalTaskDataException("Ugyldig frekvens valgt");
        }

        Task task = new Task(nextId++, title, description, user, category, frequency);
        user.addTask(task);

        return task;
    }
}
