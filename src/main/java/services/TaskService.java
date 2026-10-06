package services;

import Exceptions.IllegalTaskDataException;
import entities.*;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    static int nextId = 6;

    public Task createTask(String title, String description, User user, String categoryStr, String frequencyStr, Household household) throws IllegalTaskDataException {

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
        household.addTask(task);

        return task;
    }

    public Task findTask(int id, Household household) {
        Task task=null;
        for (Task t:household.getTasks()){
            if (t.getId()==id){
                task=t;
            }
        } return task;
    }
}
