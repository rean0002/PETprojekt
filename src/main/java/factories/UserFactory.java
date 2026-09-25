package factories;

import entities.Household;
import entities.Task;
import entities.User;
import java.util.List;
import java.util.ArrayList;

public class UserFactory {
    static List<User> users = new ArrayList<>();


    public static List<User> createUsers() {


        String[] emails = {
                "olga@mail.com", "rebecca@mail.com", "maja@mail.com"
        };
        String[] firstNames = {
                "Olga", "Rebecca", "Maja"
        };
        String[] lastNames = {
                "Hansen", "Jensen", "Sørensen"
        };
        String[] passwords = {
                "pass1", "pass2", "pass3"
        };

        Household household= new Household("Studiegruppen");


            User user1 = new User(emails[0], passwords[0], firstNames[0], lastNames[0], household);
            User user2 = new User(emails[1], passwords[1], firstNames[1], lastNames[1], household);
            User user3 = new User(emails[2], passwords[2], firstNames[2], lastNames[2], household);
            user1.setTasks(tasks());
            user2.setTasks(tasksTwo());


            household.setMember(user1);
            household.setMember(user2);
            household.setMember(user3);

            users.add(user1);
            users.add(user2);
            users.add(user3);

        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static ArrayList<Task>tasks(){

        ArrayList <Task>tasks=new ArrayList<>();

        tasks.add(new Task("vacuum", "everywhere"));
        tasks.add(new Task("dust", "bedroom"));
        tasks.add(new Task("Clean fridge", "now"));
        tasks.add(new Task("meal planning", "plan for 3 days"));

        return tasks;
    }
    public static ArrayList<Task>tasksTwo(){

        ArrayList <Task>tasks=new ArrayList<>();

        tasks.add(new Task("garbage", "plastic"));
        tasks.add(new Task("shop for groceries", ""));


        return tasks;
    }
}