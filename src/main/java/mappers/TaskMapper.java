package mappers;

import Exceptions.DatabaseException;
import entities.Routines;
import entities.Task;
import entities.TaskCategory;
import entities.TaskFrequency;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TaskMapper {
    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(TaskMapper.class);


    public Task getTaskById(int id ) throws DatabaseException {
        Task task = null;

        String query = """
                SELECT *
                FROM tasks 
                JOIN routines
                USING (routines_id)
                WHERE task_id = ?""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int task_id = rs.getInt("task_id");
                    int routines_id = rs.getInt("routines_id");
                    LocalDate dueDate = rs.getObject("due_date", LocalDate.class);

                            task= new Task (task_id, dueDate);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter opgave fejlede");
        }
        return task;
    }

    public Routines createRoutine(Routines routine, int householdId) throws DatabaseException {
        int categoryId = findCategoryId(mapCategoryToTitle(routine.getTaskCategory()));
        int frequencyId = findFrequencyId(mapFrequencyToTitle(routine.getTaskFrequency()));

        String query = "INSERT INTO routines (title, description, start_date, \"time\", frequency_id, category_id, household_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, routine.getTitle());
            stm.setString(2, routine.getDescription());
            stm.setObject(3, routine.getDate());
            stm.setObject(4, routine.getTime());
            stm.setInt(5, frequencyId);
            stm.setInt(6, categoryId);
            stm.setInt(7, householdId);
            stm.setInt(8, routine.getResponsibleUser().getId());
            stm.executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    routine.setId(rs.getInt("routines_id"));
                } else throw new DatabaseException("Rutinen kunne ikke oprettes");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Rutinen blev ikke gemt");
        }

        saveTasks(routine);

        return routine;
    }

    private void saveTasks(Routines routine) throws DatabaseException {
        String query = "INSERT INTO tasks (date_due, routines_id) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            for (Task task : routine.getTasks()) {
                stm.setObject(1, task.getDueDate());
                stm.setInt(2, routine.getId());
                stm.addBatch();
            }
            stm.executeBatch();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Opgaverne blev ikke gemt");
        }
    }

    private int findCategoryId(String title) throws DatabaseException {
        int categoryId = -1;
        String query = "SELECT category_id FROM categories WHERE title = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, title);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter kategori");
        }
        if (categoryId == -1) {
            throw new DatabaseException("Kategorien findes ikke");
        }
        return categoryId;
    }

    private int findFrequencyId(String title) throws DatabaseException {
        int frequencyId = -1;
        String query = "SELECT frequency_id FROM frequencies WHERE title = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, title);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    frequencyId = rs.getInt("frequency_id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter frekvens");
        }
        if (frequencyId == -1) {
            throw new DatabaseException("Frekvensen findes ikke");
        }
        return frequencyId;
    }

    private String mapCategoryToTitle(TaskCategory category) {
        return switch (category) {
            case CLEANING -> "Rengøring";
            case SHOPPING -> "Indkøb";
            case FOOD -> "Madlavning";
            case LAUNDRY -> "Vasketøj";
            case HOME -> "Udendørs";
            default -> "Andet";
        };
    }

    private String mapFrequencyToTitle(TaskFrequency frequency) {
        return switch (frequency) {
            case DAILY -> "Dagligt";
            case WEEKLY -> "Ugentligt";
            case MONTHLY -> "Månedligt";
            default -> "Én gang";
        };
    }






}
