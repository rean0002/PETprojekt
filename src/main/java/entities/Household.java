package entities;

import services.HouseholdService;

import java.util.ArrayList;
import java.util.TreeSet;

public class Household {

    private int id;
    private String name;
    private ArrayList<User> members;
    private ArrayList <Routines> routines;
    private final String code;

    public Household (String name){
        this.name=name;
        members= new ArrayList<>();
        routines=new ArrayList<>();
        this.code= HouseholdService.code();
    }
    public Household (int id, String name, String code){ //bruges af sql
       this.id=id;
        this.name=name;
        this.code=code;
        members= new ArrayList<>();
        routines=new ArrayList<>();
    }
    public Household (String name, String code){ //bruges når husholdning oprettes
        this.name=name;
        this.code=code;
        members= new ArrayList<>();
        routines=new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Routines> getRoutines() {
        return routines;
    }

    public void setRoutines(ArrayList<Routines> routines) {
        this.routines = routines;
    }

    public void addRoutine(Routines routine){
        routines.add(routine);
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMember(User user){
        members.add(user);
    }

    public void addMember(User user){members.add(user);}

    public String getCode() {return code;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
