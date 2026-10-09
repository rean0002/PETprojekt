package services;

import Exceptions.IllegalTaskDataException;
import entities.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class RoutinesService {

    static int nextId = 6;

    public Routines createRoutine(String title, String description, User user, String categoryStr, String frequencyStr, String dateStr, String timeStr, Household household) throws IllegalTaskDataException {

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
        //   public Routines(String title, String description, User responsibleUser,
        // bruges når rutine oprettes
        //                    TaskCategory category, TaskFrequency frequency, LocalDate date, LocalTime time){

        Routines routine = new Routines(title, description, user, category, frequency, date, time);
        household.addRoutine(routine);



        return routine;
    }


}
