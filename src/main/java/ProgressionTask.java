public class ProgressionTask extends Task{

    private int progresionIndicator=0;

    ProgressionTask (int ID, String title, User user, TaskCategory taskCategory, TaskFrequency taskFrequency){
        super(ID, title, user, taskCategory, taskFrequency);

    }

    public void logProgression (int amount){
        this.progresionIndicator=progresionIndicator+amount;
    }


}
