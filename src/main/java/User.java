import java.time.LocalDate;
import java.time.LocalTime;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int point;
    private Role role;

    public User(int id, String email, String password, String firstName, int point, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.point = point;
        this.role = role;
    }
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName=lastName;

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
        return firstName;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", point=" + point +
                ", role=" + role +
                '}';
    }
}
