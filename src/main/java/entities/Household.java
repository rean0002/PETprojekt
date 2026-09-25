package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Household {

    private String name;
    private User [] members;
    private List<Task> tasks;
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
        String [] code = new String[6];
        Random r= new Random();

        code[0]=



        return code[0]+code[1]+code[2]+code[3]+code[4]+code[5];
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
}
