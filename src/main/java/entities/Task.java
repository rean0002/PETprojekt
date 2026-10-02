package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Comparable<Task> {

    private int id;
    private String title;
    private String description;
    private User responsibleUser;
    private TaskCategory taskCategory;
    private TaskFrequency taskFrequency;

    private LocalDate date;
    private LocalTime time;
    private boolean reassign=false;
    private boolean isDone=false;
    private LocalDateTime completedAt;

    public Task(int id, String title, String description, User responsibleUser, TaskCategory taskCategory, TaskFrequency taskFrequency){
        this.id = id;
        this.title=title;
        this.description=description;
        this.responsibleUser = responsibleUser;
        this.taskCategory=taskCategory;
        this.taskFrequency=taskFrequency;
        //this.taskCategory=entities.TaskCategory.valueOf(taskCategory.toString()); //brug denne syntax når vi bruger database
        //this.taskFrequency=entities.TaskFrequency.valueOf(taskFrequency.toString());
    }
    public Task(int id, String title, String description, User responssibleUser){
        this.id = id;
        this.title=title;
        this.description=description;
        this.responsibleUser=responssibleUser;
    }




    public void reassignTask(){
        reassign=true;
    }


    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {return id;}

    public boolean isDone() {return isDone;}

    public void setDone(boolean done) {
        isDone = done;

        if(done){
        setCompletedAt();}
    }


    public String getDescription() {
        return description;
    }

    public User getResponsibleUser() {return responsibleUser;}

    public void setCompletedAt() {
       this.completedAt=LocalDateTime.now();
    }

    public void setResponsibleUser(User responsibleUser) {this.responsibleUser = responsibleUser;}

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.id, other.id);
    }
}
