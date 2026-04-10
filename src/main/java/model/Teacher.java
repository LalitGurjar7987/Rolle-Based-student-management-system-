package model;

public class Teacher {
    private int id;
    private String name;
    private String email;
    private String password;
    private String subject;

    public Teacher() {}

    public Teacher(int id, String name, String email, String password, String subject) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subject = subject;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
