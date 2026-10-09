/*package mappers;

import Exceptions.DatabaseException;
import entities.Household;
import entities.User;

import java.sql.*;

public class MapperTemplate {


    public User insertTemplate (User user) throws DatabaseException {
        String query = "INSERT INTO tabel (username, fornavn) " +
                " VALUES ?, ?";
        try (Connection connection = connectionPool.getConnection(); //try with resources connection og prepared statement
             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getUsername()); //beskriver ? 1 i query
            stm.setString(2, user.getFornavn()); //beskriver ? 2 i query
            stm.executeUpdate(); //udfør opdatering
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt("tabel_id")); //tilføj id til aktuelle User objekt
                } else throw new DatabaseException("argument kunne ikke oprettes");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Brugeren blev ikke gemt ");
        }
        return user;
    }

     */

    /*public User getUserByUserName(String userName) throws DatabaseException{
        User user = null;
        String query = "SELECT laaner_id, fornavn, efternavn, adresse, postnr, password, by " +
                "FROM laaner JOIN postnummer USING (postnr)" +
                "WHERE username = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, userName);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("laaner_id");
                    String firstName = rs.getString("fornavn");
                    String lastName = rs.getString("efternavn");
                    String address = rs.getString("adresse");
                    int zip = rs.getInt("postnr");
                    String city = rs.getString("by");
                    String password = rs.getString("password");
                    user = new User(id, userName, password, firstName, lastName, address, zip, city);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return user;
    }

       public UserAndHouseholdDTO getUserAndHousehold (User user) throws DatabaseException{
        Household household;
        UserAndHouseholdDTO userAndHouseholdDTO=null;
        String query = "SELECT name, household_id" +
                "FROM households JOIN users_households USING (household_id)" +
                "WHERE users.firstname = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, user.getFirstName());
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("household_id");
                    String name = rs.getString("navn");

                    household = new Household(id, name);
                    userAndHouseholdDTO = new UserAndHouseholdDTO(user, household);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return userAndHouseholdDTO;
    }


}
*/
