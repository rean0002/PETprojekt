package services;

import entities.User;
import factories.UserFactory;

import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        this.users = UserFactory.createUsers();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User login(String email, String password) {
        User user = getUser(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    //public User createUser() {}

    public boolean validatePassword(String password) {
        if (password == null) return false;
        return password.length() >= 8 && password.length() <= 15;
    }
}