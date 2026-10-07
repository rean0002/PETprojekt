package services;

import Exceptions.IllegalTaskDataException;
import entities.Task;
import entities.User;
import entities.TaskCategory;
import entities.TaskFrequency;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import entities.*;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    static int nextId = 6;

    public Task createTask(String title, String description, User user, String categoryStr, String frequencyStr, String dateStr, String timeStr, Household household) throws IllegalTaskDataException {

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

        if (dateStr == null || dateStr.isBlank()){
            throw new IllegalTaskDataException("Vælg venligst en dato");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new IllegalTaskDataException("Ugyldig dato");
        }
        LocalTime time = null;
        if (timeStr != null && !timeStr.isBlank()){
            try {
                time = LocalTime.parse(timeStr);
            } catch (DateTimeParseException e) {
                throw new IllegalTaskDataException("Ugyldigt tidspunkt");
            }
        }

        Task task = new Task(nextId++, title, description, user, category, frequency);
        household.addTask(task);
        task.setDate(date);
        if (time != null) {
            task.setTime(time);
        }


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
