package entities;

import java.util.ArrayList;

public class User {
    private int id;
    private String email;
    private String password;
    private int point;
    private Role role;
    private String firstName;
    private String lastName;

    private ArrayList <Task> tasks;

    // rigtige konstruktør
    public User(int id, String email, String password, int point, Role role, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.point = point;
        this.role = role;
        this.firstName= firstName;
        this.lastName= lastName;
    }
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName=lastName;

    }


    // opgave kostruktør
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public int getId() { return id; }

    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}

    public int getPoint() { return point; }
    public Role getRole() { return role; }

    public ArrayList<Task> getTasks() { return tasks; }

    public void setTasks(ArrayList<Task> tasks) { this.tasks = tasks; }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}