package mappers;

import Exceptions.DatabaseException;
import entities.Routines;
import entities.Task;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    LocalDate dueDate = rs.getObject("date_due", LocalDate.class);

                            task= new Task (task_id, dueDate);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter opgave fejlede");
        }
        return task;
    }






}
