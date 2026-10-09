package mappers;

import Exceptions.DatabaseException;
import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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


    public ArrayList<Household> households (User user) throws DatabaseException{

        ArrayList<Household>households = new ArrayList<>();

        String query = """
                SELECT name, household_id, code
                FROM households JOIN users_households USING (household_id)
                WHERE user_id = ?""";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, user.getId());

            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int id = rs.getInt("household_id");
                    String name = rs.getString("navn");
                    String code = rs.getString("code");

                    Household household = new Household(id, name, code);
                    households.add(household);

                }
            } } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return households;
    }


    public ArrayList<Task>tasks (Household household)throws DatabaseException{

        ArrayList<Task>tasks = new ArrayList<>();

        String query = """
                SELECT routines_id, 
                       task_id, 
                       r.title AS routine_title, 
                       r.description AS routine_description, 
                       user_id, 
                       u.firstname AS user_firstname, 
                       u.lastname AS user_lastname,
                       r.category_id AS category_id, 
                       r.frequency_id AS frequency_id, 
                       c.title AS category_title, 
                       f.title AS frequency_title,
                       r.start_date, 
                       r.time,
                       t.time_completed AS task_time_completed,
                       t.due_date
                FROM households h
                JOIN routines r USING (household_id)
                JOIN tasks t USING (routines_id)
                JOIN users u USING (user_id)
                JOIN categories c USING (category_id)
                JOIN frequencies f USING(frequency_id)
                WHERE household_id = ?""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, household.getId());

            try (ResultSet rs = stm.executeQuery();) {
                Map<Integer, Routines> routines = new HashMap<>();
                while (rs.next()) {

                    int routine_id = rs.getInt("routines_id");
                    int task_id = rs.getInt("task_id");
                    String title = rs.getString("routine_title");
                    String description = rs.getString("routine_description");
                    int user_id= rs.getInt("user_id");
                    String firstname = rs.getString("user_firstname");
                    String lastname = rs.getString("user_lastname");
                    int category_id= rs.getInt("category_id");
                    int frequency_id = rs.getInt("frequency_id");
                    String category_title = rs.getString("category_title");
                    String frequency_title = rs.getString("frequency_title");
                    LocalDate startDate = rs.getObject("start_date", LocalDate.class);
                    LocalDate dueDate = rs.getObject("due_date", LocalDate.class);

                    Time sqltaskTime = rs.getTime("task_time_completed");
                    LocalTime time_completed = (sqltaskTime != null) ? sqltaskTime.toLocalTime() : null;

                    Time sqlTime = rs.getTime("time");
                    LocalTime time = (sqlTime != null) ? sqlTime.toLocalTime() : null;

                    TaskCategory category = TaskCategory.valueOf(category_title);
                    TaskFrequency frequency = TaskFrequency.valueOf(frequency_title);

                    Routines routine = routines.get(routine_id);

                    if (routine == null) {
                        User responsibleUser = new User(user_id, firstname, lastname);
                        routine = new Routines(routine_id, title, description, responsibleUser, category, frequency, startDate, time);
                        routines.put(routine_id, routine);
                    }

                    Task task = new Task(task_id, routine, dueDate);

                    task.setTimeCompleted(time_completed);

                    tasks.add(task);


                }
            } } catch (SQLException e) {
        logger.error(e.getMessage());
        throw new DatabaseException("Søgning efter opgaver fejlede");
    }
        return tasks;
}

    }









