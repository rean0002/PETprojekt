package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {

    private int ID;
    private String title;
    private String description;
    private User responsibleUser;
    private TaskCategory taskCategory;
    private TaskFrequency taskFrequency;

    private LocalDate date;
    private LocalTime time;
    private boolean reassign=false;
    private boolean isDone=false;

    public Task(int ID, String title, String description, User responsibleUser, TaskCategory taskCategory, TaskFrequency taskFrequency){
        this.ID=ID;
        this.title=title;
        this.description=description;
        this.responsibleUser = responsibleUser;
        this.taskCategory=taskCategory;
        this.taskFrequency=taskFrequency;
        //this.taskCategory=entities.TaskCategory.valueOf(taskCategory.toString()); //brug denne syntax når vi bruger database
        //this.taskFrequency=entities.TaskFrequency.valueOf(taskFrequency.toString());
    }
    public Task(String title, String description, User responssibleUser){
        this.title=title;
        this.description=description;
        this.responsibleUser=responssibleUser;
    }




    public void reassignTask(){
        reassign=true;
    }


    public void markAsDone (){ isDone=true;}


    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getResponsibleUser() {return responsibleUser;}

    public void setResponsibleUser(User responsibleUser) {this.responsibleUser = responsibleUser;}
}
