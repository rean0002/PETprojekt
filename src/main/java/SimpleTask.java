public class SimpleTask extends Task {

    //Class SimpleTask extends Task {
    //- boolean iDone
    //+ void markAsDone()
    //+ void undoIsDone()
    //}

    private boolean isdone = false;

    SimpleTask (int ID, String title, String description, User user, TaskCategory taskCategory, TaskFrequency taskFrequency){
        super(ID, title, description, user, taskCategory, taskFrequency);
    }

    public void markAsDone(){
        this.isdone=true;
    }

    public void undoIDone(){
        this.isdone=false;
    }



}
