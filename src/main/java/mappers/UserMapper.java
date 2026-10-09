package mappers;

import Exceptions.DatabaseException;
import entities.Household;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserMapper {

    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(UserMapper.class);


    public User createUser (User user) throws DatabaseException {
        String query = "INSERT INTO users (firstname, lastname, email, password) " +
                " VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPassword());
            stm.executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt("user_id"));
                } else throw new DatabaseException("argument kunne ikke oprettes");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Brugeren blev ikke gemt ");
        }
        return user;
    }

    public User getUserByUserId(int id ) throws DatabaseException {
        User user = null;

        String query = "SELECT user_id, firstname, lastname, email, password " +
                "FROM users " +
                "WHERE user_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    user = new User(email, password, firstName, lastName);
                    user.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return user;
    }
    public User getUserByEmailAndPassword(String email, String password)  throws DatabaseException {
        User user = null;

        String query = "SELECT user_id, firstname, lastname, email, password " +
                "FROM users " +
                "WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, email);
            stm.setString(2, password);

            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    user = new User(email, password, firstName, lastName);
                    user.setId(userId);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return user;
    }

    public void joinHousehold(int userId, String code) throws DatabaseException {
        int householdId = findHouseholdIdByCode(code);
        int roleId = findRoleIdByTitle("Medlem");
        insertUserHousehold(userId, householdId, roleId);
    }

    public void addUserAsAdmin(int userId, int householdId) throws DatabaseException {
        int roleId = findRoleIdByTitle("Admin");
        insertUserHousehold(userId, householdId, roleId);
    }

    private void insertUserHousehold(int userId, int householdId, int roleId) throws DatabaseException {
        String query = "INSERT INTO users_households (users_user_id, households_household_id, role_id) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, userId);
            stm.setInt(2, householdId);
            stm.setInt(3, roleId);
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Brugeren kunne ikke tilknyttes husstanden");
        }
    }

    private int findHouseholdIdByCode(String code) throws DatabaseException {
        int householdId = -1;// -1 betyder "ikke fundet endnu"
        String query = "SELECT household_id " +
                "FROM households " +
                "WHERE households.code = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, code);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    householdId = rs.getInt("household_id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter husstand");
        }

        if (householdId == -1) {
            throw new DatabaseException("Forkert kode");
        } return householdId;
    }

    private int findRoleIdByTitle(String title) throws DatabaseException {
        int roleId = -1;
        String query2 = "SELECT roles_id " +
                "FROM roles " +
                "WHERE roles.title = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query2)) {
            stm.setString(1, title);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    roleId = rs.getInt("roles_id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter rolle");
        }

        if (roleId == -1) {
            throw new DatabaseException("Rollen findes ikke");
        } return roleId;
    }

}
