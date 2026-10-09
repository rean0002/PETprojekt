//package mappers;
//
//import Exceptions.DatabaseException;
//import entities.User;
//
//import java.sql.*;

//public class UserMapper {

//    public User createUser (User user) throws DatabaseException {
//        String query = "INSERT INTO users (firstname, lastname, email, password) " +
//                " VALUES (?, ?, ?, ?)";
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stm.setString(1, user.getFirstName());
//            stm.setString(2, user.getLastName());
//            stm.setString(3, user.getEmail());
//            stm.setString(4, user.getPassword());
//            stm.executeUpdate();
//            try (ResultSet rs = stm.getGeneratedKeys()) {
//                if (rs.next()) {
//                    user.setId(rs.getInt("user_id"));
//                } else throw new DatabaseException("argument kunne ikke oprettes");
//            }
//
//        } catch (SQLException e) {
//            logger.error(e.getMessage());
//            throw new DatabaseException("Brugeren blev ikke gemt ");
//        }
//        return user;
//    }
//
//    public User getUserByUserId(int id ) throws DatabaseException {
//        User user = null;
//
//        String query = "SELECT user_id, firstname, lastname, email, password " +
//                "FROM users " +
//                "WHERE user_id = ?";
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement stm = connection.prepareStatement(query)) {
//            stm.setInt(1, id);
//            try (ResultSet rs = stm.executeQuery();) {
//                if (rs.next()) {
//                    String firstName = rs.getString("firstname");
//                    String lastName = rs.getString("lastname");
//                    String email = rs.getString("email");
//                    String password = rs.getString("password");
//                    user = new User(email, password, firstName, lastName);
//                    user.setId(id);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage());
//            throw new DatabaseException("Søgning efter brugeren fejlede");
//        }
//        return user;
//    }
//}
