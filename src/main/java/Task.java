import jdk.jfr.Frequency;

public class Task {

    private int ID;
    private String title;
    private User user;
    private Category category;
    private Frequency frequency;
    private boolean reassign=false;
    private localDate date;
    private localTime time;

    Task (int ID, String title, User user, Category category, Frequency frequency, boolean reassign)




    public void reassignTask(){
        reassign=true;
    }



}
