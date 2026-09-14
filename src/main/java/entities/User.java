package entities;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String name;
    private int point;
    private Role role;

    // rigtige konstruktør
    public User(int id, String email, String password, String name, int point, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.point = point;
        this.role = role;
    }

    // opgave kostruktør
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public int getPoint() { return point; }
    public Role getRole() { return role; }
}