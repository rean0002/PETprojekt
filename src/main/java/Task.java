import java.time.LocalDate;
import java.time.LocalTime;

public class Task {

    private int ID;
    private String title;
    private String description;
    private User user;
    private TaskCategory taskCategory;
    private TaskFrequency taskFrequency;

    private LocalDate date;
    private LocalTime time;
    private boolean reassign=false;
    private boolean isDone=false;

    Task (int ID, String title, String description, User user, TaskCategory taskCategory, TaskFrequency taskFrequency){
        this.ID=ID;
        this.title=title;
        this.description=description;
        this.user=user;
        this.taskCategory=taskCategory;
        this.taskFrequency=taskFrequency;
        //this.taskCategory=TaskCategory.valueOf(taskCategory.toString()); //brug denne syntax når vi bruger database
        //this.taskFrequency=TaskFrequency.valueOf(taskFrequency.toString());
    }




    public void reassignTask(){
        reassign=true;
    }


    public void markAsDone (){ isDone=true;}


    public TaskCategory getTaskCategory() {
        return taskCategory;
    }
}
