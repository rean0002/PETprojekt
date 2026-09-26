package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Household {

    private String name;
    private ArrayList<User> members;
    private ArrayList<Task> tasks;
    private final String code;

    public Household (String name){
        this.name=name;
        members= new ArrayList<>();
        tasks=new ArrayList<>();
        this.code=code();
    }


    public ArrayList<Task> setHouseholdTasks (){
        tasks= new ArrayList<>();
        for(User user:members){
            for(Task task:user.getTasks()){
                tasks.add(task);
            }
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

    public ArrayList<Task>getTasks(){
        for (User u: members){
            for(Task task:u.getTasks()){
                tasks.add(task);
            }
        }
        return tasks;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMember(User user){
        members.add(user);
    }

}
