package factories;

import entities.User;
import java.util.List;
import java.util.ArrayList;

public class UserFactory {

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();

        String[] emails = {
                "olga@mail.com", "rebecca@mail.com"
        };

        for (int i = 0; i < emails.length; i++) {
            users.add(new User(emails[i], "password" + (i + 1)));
        }

        return users;
    }
}