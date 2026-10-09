package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Comparable<Task> {

    private int id;
    private Routines routines;
    private LocalDate dueDate;
    private LocalTime completedAt;

    /*public Task(int id, String title, String description, User responsibleUser, TaskCategory taskCategory, TaskFrequency taskFrequency, LocalDate date){
        this.id = id;
        this.title=title;
        this.description=description;
        this.responsibleUser = responsibleUser;
        this.taskCategory=taskCategory;
        this.taskFrequency=taskFrequency;
        this.date=date;
        //this.taskCategory=entities.TaskCategory.valueOf(taskCategory.toString()); //brug denne syntax når vi bruger database
        //this.taskFrequency=entities.TaskFrequency.valueOf(taskFrequency.toString());
    }*/

    public Task (int id, Routines routines, LocalDate dueDate){
        this.id=id;
        this.routines = routines;
        this.dueDate=dueDate;
    }

    public boolean isDone() {
        return completedAt != null;
    }



    public String getTitle() {
        return routines.getTitle();
    }

    public int getId() {return id;}

    public LocalDate getDate() {return routines.getDate();}



    public void markAsDone() {
        this.completedAt = LocalTime.now();
    }


    public String getDescription() {
        return routines.getDescription();
    }


    public User getResponsibleUser() {return routines.getResponsibleUser();}

    public void setCompletedAt() {
       this.completedAt=LocalTime.now();
    }

    public void setTimeCompleted(LocalTime timeCompleted) {
        this.completedAt = timeCompleted;
    }

    public LocalTime getCompletedAt() {return completedAt;}

    public void setResponsibleUser(User responsibleUser) {routines.setResponsibleUser(responsibleUser);}

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.id, other.id);
    }
}
