package factories;

import entities.User;
import java.util.List;
import java.util.ArrayList;

public class UserFactory {

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();

        String[] emails = {
                "amalie@mail.com", "rebecca@mail.com", "mette@mail.com", "lasse@mail.com",
                "sofie@mail.com", "mikkel@mail.com", "freja@mail.com", "noah@mail.com",
                "clara@mail.com", "victor@mail.com"
        };

        for (int i = 0; i < emails.length; i++) {
            users.add(new User(emails[i], "password" + (i + 1)));
        }

        return users;
    }
}