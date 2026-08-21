import java.time.LocalDate;
import java.time.LocalTime;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private int point;

    public User(int id, String email, String password, String name, int point) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.point = point;
    }

    public Task createTask(String title, String description, Category category,
                           LocalDate date, LocalTime time, Frequency frequency, boolean reassignable) {
        return new Task(title, description, category, date, time, frequency, this, reassignable);
    }
    public void taskDone(Task task) {
        task.markAsDone();
        this.point += 1;
    }

    public Reminder createReminder(String description, LocalDate date, LocalTime time) {
        return new Reminder(description, date, time, this);
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
