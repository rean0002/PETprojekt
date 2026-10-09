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
    private boolean reassign = false;


    public Routines(int id, String title, String description, User responsibleUser,
                    TaskCategory category, TaskFrequency frequency, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsibleUser = responsibleUser;
        this.taskCategory = category;
        this.taskFrequency = frequency;
        this.date = date;
        this.time = time;
    }

    public Routines(String title, String description, User responsibleUser, //bruges når rutine oprettes
                    TaskCategory category, TaskFrequency frequency, LocalDate date, LocalTime time) {
        this.title = title;
        this.description = description;
        this.responsibleUser = responsibleUser;
        this.taskCategory = category;
        this.taskFrequency = frequency;
        this.date = date;
        this.time = time;
        ArrayList<Task> tasks = tasks(date, frequency);
    }


    private ArrayList<Task> tasks(LocalDate date, TaskFrequency taskFrequency) {

        ArrayList<Task> tasks = null;

        switch (taskFrequency) {
            case NONE:
                 {
                    Task task = new Task(this, date);
                    tasks.add(task);
                }
                break;
            case DAILY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    date = date.plusDays(1);
                    tasks.add(task);
                }
                break;
            case EVERY_OTHER_DAY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    date = date.plusDays(2);
                    tasks.add(task);
                }
                break;
            case WEEKLY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    date = date.plusWeeks(1);
                    tasks.add(task);
                }
                break;
            case BI_WEEKLY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    if(i%2==0){
                    date = date.plusDays(3);}
                    if(i%2==1){
                    date = date.plusDays(4);}
                    tasks.add(task);
                }
                break;
            case MONTHLY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    date = date.plusMonths(1);
                    tasks.add(task);
                }
                break;
            case BI_MONTHLY:
                for (int i = 0; i < 20; i++) {
                    Task task = new Task(this, date);
                    date = date.plusWeeks(2);
                    tasks.add(task);
                }
                break;
        }

        return tasks;
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

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public TaskFrequency getTaskFrequency() {
        return taskFrequency;
    }
}
