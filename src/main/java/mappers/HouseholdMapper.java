package mappers;

import Exceptions.DatabaseException;
import entities.Household;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class HouseholdMapper {
    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(HouseholdMapper.class);


    public Household createHousehold (Household household) throws DatabaseException {
        String query = "INSERT INTO households (name, code) " +
                " VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, household.getName());
            stm.setString(2, household.getCode());
            stm.executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    household.setId(rs.getInt("household_id"));
                } else throw new DatabaseException("Husholdningen kunne ikke oprettes");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Husholdningen blev ikke gemt ");
        }
        return household;
    }

      public Household getHouseholdById(Household h) throws DatabaseException{
        Household household = null;
        String query = "SELECT household_id, name, code " +
                "FROM households" +
                "WHERE id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, h.getId());
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("household_id");
                    String name = rs.getString("name");
                    String code = rs.getString("code");

                    household = new Household(id, name, code);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return household;
    }



    public ArrayList <Household> getUsersHouseholds(int userId) throws DatabaseException{ //hent alle housholds som en bruger er medlem af
       ArrayList <Household> households=null;
        Household household = null;

        String query =
                "SELECT * FROM users_households" +
                "JOIN households" +
                "ON houssehold_id=households_household_id" +
                "WHERE users_user_id = ?;";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("household_id");
                    String name = rs.getString("name");
                    String code = rs.getString("code");

                    household = new Household(id, name, code);
                    households.add(household);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return households;
    }





}
