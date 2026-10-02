package services;
import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;

import java.util.List;

public class UserService {

    public static void addUser(User user) throws IllegalUserDataException {
        if (user == null){
            throw new IllegalUserDataException("Brugeren findes ikke");
        }
        UserFactory.addUser(user);
    }

    public User getUser(String email) throws IllegalUserDataException{
        User user=null;

        for (User u : UserFactory.getUsers()) {
            if (u.getEmail().equals(email)) {
                user=u;
            } }
        if (user==null){ throw new IllegalUserDataException("Email matcher ikke nogen eksisterende bruger");}
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
            throw new IllegalUserDataException("Udfyld navn");
        }
        if (lastName == null || lastName.isBlank()){
            throw new IllegalUserDataException("Udfyld efternavn");
        }
        if (email == null || email.isBlank()){
            throw new IllegalUserDataException("Udfyld email adresse");
        }
        if (password == null || password.isBlank()){
            throw new IllegalUserDataException("Udfyld password");
        }
        if(!validatePassword(password)){
            throw new IllegalUserDataException("password skal være mellem 8-15 tegn");
        }
        if(emailExists(email)){
            throw new IllegalUserDataException("email er allerede i brug");
        }



        User user=new User(email, password, UtilService.capitalizeFirst(firstName), UtilService.capitalizeFirst(lastName));

        addUser(user);

        return user;
    }

    public User getUserByFirstName(String firstName) throws IllegalUserDataException {
        User user = null;

        for (User u : UserFactory.getUsers()) {
            if (u.getFirstName().equals(firstName)) {
                user = u;
            }
        } if (user == null){
            throw new IllegalUserDataException("Email matcher ikke nogen eksisterende bruger");
        }
        return null;
    }

}