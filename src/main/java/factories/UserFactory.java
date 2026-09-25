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
                "olga@mail.com", "rebecca@mail.com"
        };
        String[] firstNames = {
                "Olga", "Rebecca"
        };
        String[] lastNames = {
                "Hansen", "Jensen"
        };
        String householdName = "Familien Jensen";

        for (int i = 0; i < emails.length; i++) {
            User user = new User(emails[i], "password" + (i + 1), firstNames[i], lastNames[i], new Household(householdName));
            user.setTasks(tasks());
            users.add(user);
        }

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
}