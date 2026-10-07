package mappers;

import java.sql.*;

public class MapperTemplate {

    /*
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
                    argument.setId(rs.getInt("tabel_id")); //tilføj id til aktuelle User objekt
                } else throw new DatabaseException("argument kunne ikke oprettes");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Brugeren blev ikke gemt ");
        }
        return user;
    }

     */

}
