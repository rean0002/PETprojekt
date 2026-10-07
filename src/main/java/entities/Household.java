package entities;

import services.HouseholdService;

import java.util.ArrayList;
import java.util.TreeSet;

public class Household {

    private String name;
    private ArrayList<User> members;
   // private TreeSet<Task> tasks;
    private ArrayList <Task> tasks;
    private final String code;

    public Household (String name){
        this.name=name;
        members= new ArrayList<>();
        tasks=new ArrayList<>();
        this.code= HouseholdService.code();
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

    public ArrayList<Task>getTasks(){
        return tasks;
    }

    /*public void setTasks(TreeSet<Task> tasks) {
        this.tasks = tasks;
    }*/

    public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}


    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMember(User user){
        members.add(user);
    }

    public void addMember(User user){members.add(user);}

}
