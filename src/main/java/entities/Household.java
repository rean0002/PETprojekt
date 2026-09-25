package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Household {

    private String name;
    private User [] members;
    private ArrayList<Task> tasks;
    private  String code;

    public Household (String name){
        this.name=name;
        members= new User [10];
        tasks= new ArrayList<>();
        this.code=code();
    }


    public ArrayList<Task> householdTasks (){
        ArrayList <Task>tasks= new ArrayList<>();
        for(User user:members){
            tasks.addAll(user.getTasks());
        }
        return tasks;
    }

    public String code(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String [] code = new String[6];
        Random r= new Random();

        code[0]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[1]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[2]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[3]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[4]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        code[5]= String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));


        return (code[0]+code[1]+code[2]+code[3]+code[4]+code[5]);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask (Task task){
        tasks.add(task);
    }

    public ArrayList<Task>tasks(){return tasks; }


}
