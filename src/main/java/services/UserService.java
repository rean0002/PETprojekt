package services;
import Exceptions.DatabaseException;
import Exceptions.IllegalUserDataException;
import entities.User;
import factories.UserFactory;
import mappers.ConnectionPool;
import mappers.UserMapper;

import java.util.List;

public class UserService {
    private UserMapper userMapper;


    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;

    }

    public static void addUser(User user) throws IllegalUserDataException {
        if (user == null) {
            throw new IllegalUserDataException("Brugeren findes ikke");
        }
        UserFactory.addUser(user);
    }

    public User getUser(String email) throws IllegalUserDataException {
        User user = null;

        for (User u : UserFactory.currentHousehold().getMembers()) {
            if (u.getEmail().equals(email)) {
                user = u;
            }
        }
        if (user == null) {
            throw new IllegalUserDataException("Email matcher ikke nogen eksisterende bruger");
        }
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
        try {
            User user = userMapper.getUserByEmailAndPassword(email, password);
            if (user == null) {
                throw new IllegalUserDataException("Email eller password er forkert");
            }
            return user;
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Der opstod en fejl ved login");
        }
    }

    public boolean validatePassword(String password) throws IllegalUserDataException {
        if (password == null) {
            throw new IllegalUserDataException("password er null");
        }
        return password.length() >= 8 && password.length() <= 15;
    }


    public User createUser(String firstName, String lastName, String email, String password) throws IllegalUserDataException {

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalUserDataException("Udfyld navn");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalUserDataException("Udfyld efternavn");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalUserDataException("Udfyld email adresse");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalUserDataException("Udfyld password");
        }
        if (!validatePassword(password)) {
            throw new IllegalUserDataException("password skal være mellem 8-15 tegn");
        }
        if (emailExists(email)) {
            throw new IllegalUserDataException("email er allerede i brug");
        }

        try {
            User user = new User(email, password, UtilService.capitalizeFirst(firstName), UtilService.capitalizeFirst(lastName));
            userMapper.createUser(user);
            return user;
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Der opstod en fejl ved oprettelse af bruger");
        }
    }

    public User getUserByFirstName(String firstName) throws IllegalUserDataException {
        User user = null;

        for (User u : UserFactory.currentHousehold().getMembers()) {
            if (u.getFirstName().equals(firstName)) {
                user = u;
            }
        }
        if (user == null) {
            throw new IllegalUserDataException("Email matcher ikke nogen eksisterende bruger");
        }
        return user;
    }

    public void joinHousehold(int userId, String code) throws IllegalUserDataException {
        try {
            userMapper.joinHousehold(userId, code);
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Forkert kode, eller husstanden findes ikke");
        }
    }

    public void makeAdmin(int userId, int householdId) throws IllegalUserDataException {
        try {
            userMapper.addUserAsAdmin(userId, householdId);
        } catch (DatabaseException e) {
            throw new IllegalUserDataException("Kunne ikke gøre brugeren til admin");
        }
    }
}