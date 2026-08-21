public class SimpleTask extends Task {

    //Class SimpleTask extends Task {
    //- boolean iDone
    //+ void markAsDone()
    //+ void undoIsDone()
    //}

    private boolean isdone = false;

    SimpleTask (int ID, String title, User user, TaskCategory taskCategory, TaskFrequency taskFrequency){
        super(ID, title, user, taskCategory, taskFrequency);
    }

    public void markAsDone(){
        this.isdone=true;
    }

    public void undoIDone(){
        this.isdone=false;
    }



}
