import java.time.LocalDate;
import java.time.LocalTime;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private int point;
    private Role role;

    public User(int id, String email, String password, String name, int point, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.point = point;
        this.role = role;
    }

    public Task createSimpleTask(int ID, String title, String description, User user, TaskCategory taskCategory, TaskFrequency taskFrequency) {
        return new SimpleTask(ID, title, description, user, taskCategory, taskFrequency);
    }

    public void taskDone(Task task) {
        task.markAsDone();
        this.point += 1;
    }

    public Reminder createReminder(String description, LocalDate date, LocalTime time) {
        return new Reminder(description, date, time);
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
