package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Routines {


        private int id;
        private String title;
        private String description;
        private User responsibleUser;
        private TaskCategory taskCategory;
        private TaskFrequency taskFrequency;

        private LocalDate date;
        private LocalTime time;
        private boolean reassign=false;



    public Routines(int id, String title, String description, User responsibleUser,
                    TaskCategory category, TaskFrequency frequency, LocalDate date, LocalTime time){
            this.id=id;
            this.title=title;
            this.description=description;
            this.responsibleUser=responsibleUser;
            this.taskCategory=category;
            this.taskFrequency=frequency;
            this.date=date;
            this.time=time;
    }
    public Routines(String title, String description, User responsibleUser, //bruges når rutine oprettes
                    TaskCategory category, TaskFrequency frequency, LocalDate date, LocalTime time){
        this.title=title;
        this.description=description;
        this.responsibleUser=responsibleUser;
        this.taskCategory=category;
        this.taskFrequency=frequency;
        this.date=date;
        this.time=time;
    }


    public ArrayList<Task> tasks (){

    }


    public void reassignTask(){
        reassign=true;
    }


    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public TaskFrequency getTaskFrequency() {
        return taskFrequency;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
}
