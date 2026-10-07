package entities;

import java.time.LocalDate;

public class SimpleTask extends Task {

    //Class entities.SimpleTask extends entities.Task {
    //- boolean iDone
    //+ void markAsDone()
    //+ void undoIsDone()
    //}

    private boolean isdone = false;

    SimpleTask (int ID, String title, String description, User user, TaskCategory taskCategory, TaskFrequency taskFrequency, LocalDate date){
        super(ID, title, description, user, taskCategory, taskFrequency, date);
    }

    public void markAsDone(){
        this.isdone=true;
    }

    public void undoIDone(){
        this.isdone=false;
    }



}
