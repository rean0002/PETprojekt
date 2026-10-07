package entities;

import java.time.LocalDate;

public class ProgressionTask extends Task {

    private int progresionIndicator=0;

    ProgressionTask (int ID, String title, String description, User user, TaskCategory taskCategory, TaskFrequency taskFrequency, LocalDate date){
        super(ID, title, description, user, taskCategory, taskFrequency, date);

    }

    public void logProgression (int amount){
        this.progresionIndicator=progresionIndicator+amount;
    }


}
