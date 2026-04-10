package model;

public class StudentProfile {

    private int id;
    private int studentId;
    private String phone;
    private String address;
    private String className;
    private String dob;

    public StudentProfile() {}

    public StudentProfile(int studentId, String phone, String address, String className, String dob) {
        this.studentId = studentId;
        this.phone = phone;
        this.address = address;
        this.className = className;
        this.dob = dob;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
}
