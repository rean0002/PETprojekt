package factories;

import entities.*;
import services.TaskService;

import java.util.List;
import java.util.ArrayList;



public class UserFactory {
    static List<User> users = new ArrayList<>();
    static int nextId = 1;
    static Household household;


    static TaskService taskservice=new TaskService();


    public static Household currentHousehold () {

        if (household != null) {
            return household;
        }

        household= new Household("Studiegruppen");

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


            User user1 = new User(emails[0], passwords[0], firstNames[0], lastNames[0]);
            User user2 = new User(emails[1], passwords[1], firstNames[1], lastNames[1]);
            User user3 = new User(emails[2], passwords[2], firstNames[2], lastNames[2]);

            user1.setHousehold(household);
            user2.setHousehold(household);
            user3.setHousehold(household);

            household.setMember(user1);
            household.setMember(user2);
            household.setMember(user3);

            taskOne(user1);
            tasksTwo(user2);



            return household;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void taskOne (User user){


        household.addTask(new Task(
                nextId++,
                "Rengør køkken",
                "Tør bordene af",
                user,
                TaskCategory.HOME,
                TaskFrequency.DAILY
        ));

        household.addTask(new Task(
                nextId++,
                "støvsug",
                "soveværelse",
                user,
                TaskCategory.CLEANING,
                TaskFrequency.WEEKLY
        ));

        household.addTask(new Task(
                nextId++,
                "Madplan",
                "find på 2 retter",
                user,
                TaskCategory.FOOD,
                TaskFrequency.NONE
        ));

    }


    public static void tasksTwo(User user){


        household.addTask(new Task(
                nextId++,
                "Tag skrald ud",
                "efter aftensmad",
                user,
                TaskCategory.TRASH,
                TaskFrequency.WEEKLY
        ));
        household.addTask(new Task(
                nextId++,
                "Køb ind",
                "husk fødselsdagskort",
                user,
                TaskCategory.HOME,
                TaskFrequency.NONE
        ));

    }
}