/*package mappers;

import Exceptions.DatabaseException;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class TaskMapper {


    public Task getTaskById(int id ) throws DatabaseException {
        Task task = null;

        String query = """
                SELECT *
                FROM tasks 
                WHERE task_id = ?""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    int task_id = rs.getInt("task_id");
                    int routines_id = rs.getInt("routines_id");
                    LocalTime time=
                            Task task= new Task (task_id, )

                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DatabaseException("Søgning efter brugeren fejlede");
        }
        return task;
    }


}
*/