package factories;

import entities.*;
import services.TaskService;

import java.util.List;
import java.util.ArrayList;



public class UserFactory {
    static List<User> users = new ArrayList<>();
    static int nextId = 1;

    static TaskService taskservice=new TaskService();


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


            user1.setTasks(tasks(user1));
            household.setMember(user1);


            household.setMember(user2);
            user2.setTasks(tasksTwo(user2));


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

    public static ArrayList<Task>tasks(User user){

        ArrayList <Task>tasks=new ArrayList<>();



        tasks.add(new Task(
                nextId++,
                "Rengør køkken",
                "Tør bordene af",
                user,
                TaskCategory.HOME,
                TaskFrequency.DAILY
        ));
        tasks.add(new Task(
                nextId++,
                "støvsug",
                "soveværelse",
                user,
                TaskCategory.CLEANING,
                TaskFrequency.WEEKLY
        ));
        tasks.add(new Task(
                nextId++,
                "Madplan",
                "find på 2 retter",
                user,
                TaskCategory.FOOD,
                TaskFrequency.NONE
        ));



       /* tasks.add(new Task(1,"vacuum", "everywhere", user));
        tasks.add(new Task(2,"dust", "bedroom", user));
        tasks.add(new Task(3,"Clean fridge", "now", user));
        tasks.add(new Task(4,"meal planning", "plan for 3 days", user));

        */

        return tasks;
    }
    public static ArrayList<Task>tasksTwo(User user){

        ArrayList <Task>tasks=new ArrayList<>();

        /*tasks.add(new Task(5,"garbage", "plastic", user));
        tasks.add(new Task(6,"shop for groceries", "", user));
         */

        tasks.add(new Task(
                nextId++,
                "Tag skrald ud",
                "efter aftensmad",
                user,
                TaskCategory.TRASH,
                TaskFrequency.WEEKLY
        ));
        tasks.add(new Task(
                nextId++,
                "Køb ind",
                "husk fødselsdagskort",
                user,
                TaskCategory.HOME,
                TaskFrequency.NONE
        ));


        return tasks;
    }
}