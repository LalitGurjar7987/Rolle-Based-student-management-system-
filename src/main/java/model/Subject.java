package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Subject;
import util.DBUtil;

public class Subject {
    private int id;
    private String subjectName;
    private int teacherId;

    public Subject() {}

    public Subject(int id, String subjectName, int teacherId) {
        this.id = id;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
 // Get a student by email (if not already present)
    public Student getStudentByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPassword(rs.getString("password"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 // New: fetch subjects for a student (via student_subjects join)
    public List<Subject> getSubjectsForStudent(int studentId) {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT sub.id, sub.subject_name, sub.teacher_id " +
                     "FROM subjects sub " +
                     "JOIN student_subjects ss ON sub.id = ss.subject_id " +
                     "WHERE ss.student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject sub = new Subject();
                    sub.setId(rs.getInt("id"));
                    sub.setSubjectName(rs.getString("subject_name"));
                    sub.setTeacherId(rs.getInt("teacher_id"));
                    list.add(sub);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
