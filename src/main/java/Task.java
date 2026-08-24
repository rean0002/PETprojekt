

public class Task {

    private int ID;
    private String title;
    private User user;
    private TaskCategory taskCategory;
    private TaskFrequency taskFrequency;
    private boolean reassign=false;
    private localDate date;
    private localTime time;

    Task (int ID, String title, User user, TaskCategory taskCategory, TaskFrequency taskFrequency){
        this.ID=ID;
        this.title=title;
        this.user=user;
        this.taskCategory=TaskCategory.valueOf(taskCategory.toString()); //i tvivl om hvorfor der skal totring på, når der ikke skal det i sp4 dieae type
        this.taskFrequency=TaskFrequency.valueOf(taskFrequency.toString());
    }




    public void reassignTask(){
        reassign=true;
    }



}
