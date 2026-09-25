package services;
import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;

import java.util.List;

public class UserService {

    public static void addUser(User user) {
        UserFactory.addUser(user);
    }

    public User getUser(String email) throws IllegalUserDataException{
        User user=null;

        for (User u : UserFactory.getUsers()) {
            if (u.getEmail().equals(email)) {
                user=u;
            } }
        if (user==null){ throw new IllegalUserDataException("Email does not match existing user");}
        return user;
    }

    public boolean emailExists(String email) {
        for (User u : UserFactory.getUsers()) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User login(String email, String password) throws IllegalUserDataException {
        User user = getUser(email);
        if (!user.getPassword().equals(password)) {
            throw new IllegalUserDataException("Password er forkert");
        }
        return user;
    }

    public boolean validatePassword(String password)throws IllegalUserDataException {
        if (password == null) {throw new IllegalUserDataException("password er null");}
        return password.length() >= 8 && password.length() <= 15;
    }


    public User createUser(String firstName, String lastName, String email, String password) throws IllegalUserDataException {

        if (firstName == null || firstName.isBlank()){
            throw new IllegalUserDataException("Please fill out name");
        }
        if (lastName == null || lastName.isBlank()){
            throw new IllegalUserDataException("Please fill out lastname");
        }
        if (email == null || email.isBlank()){
            throw new IllegalUserDataException("Please fill out email address");
        }
        if (password == null || password.isBlank()){
            throw new IllegalUserDataException("Please fill out password");
        }
        if(!validatePassword(password)){
            throw new IllegalUserDataException("password needs to be ... criteria");
        }
        if(emailExists(email)){
            throw new IllegalUserDataException("email address is already used by another user");
        }

        User user=new User(email, password, firstName, lastName);

        addUser(user);

        return user;
    }

    public User getUserByFirstName(String firstName) {
        for (User u : UserFactory.getUsers()) {
            if (u.getFirstName().equals(firstName)) {
                return u;
            }
        }
        return null;
    }

}