package factories;

import entities.User;
import java.util.List;
import java.util.ArrayList;

public class UserFactory {
    static List<User> users = new ArrayList<>();

    public static List<User> createUsers() {
        String[] emails = {
                "olga@mail.com", "rebecca@mail.com"
        };

        for (int i = 0; i < emails.length; i++) {
            users.add(new User(emails[i], "password" + (i + 1)));
        }

        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }
}