package dao;

import java.sql.*;
import java.util.*;

import model.Student;
import model.Subject;
import util.DBUtil;

public class StudentDAO {

    // ================= ADD STUDENT =================
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPassword());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                // auto-create login
                String userSql =
                        "INSERT INTO users (username, password, role) VALUES (?, ?, 'STUDENT')";
                PreparedStatement ups = conn.prepareStatement(userSql);
                ups.setString(1, s.getEmail());
                ups.setString(2, s.getPassword());
                ups.executeUpdate();
            }

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= GET ALL STUDENTS (ADMIN) =================
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= GET STUDENT BY EMAIL (LOGIN) =================
    public Student getStudentByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= SUBJECTS =================
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setSubjectName(rs.getString("subject_name"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= STUDENT → SUBJECTS =================
    public List<Subject> getSubjectsForStudent(int studentId) {
        List<Subject> list = new ArrayList<>();

        String sql =
                "SELECT s.id, s.subject_name " +
                "FROM subjects s " +
                "JOIN student_subjects ss ON s.id = ss.subject_id " +
                "WHERE ss.student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setSubjectName(rs.getString("subject_name"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Set<Integer> getEnrolledSubjectIds(int studentId) {
        Set<Integer> set = new HashSet<>();
        String sql = "SELECT subject_id FROM student_subjects WHERE student_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                set.add(rs.getInt("subject_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public void saveStudentSubjects(int studentId, String[] subjectIds) {
        String deleteSQL = "DELETE FROM student_subjects WHERE student_id=?";
        String insertSQL =
                "INSERT INTO student_subjects (student_id, subject_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection()) {

            PreparedStatement del = conn.prepareStatement(deleteSQL);
            del.setInt(1, studentId);
            del.executeUpdate();

            if (subjectIds != null) {
                PreparedStatement ins = conn.prepareStatement(insertSQL);
                for (String sid : subjectIds) {
                    ins.setInt(1, studentId);
                    ins.setInt(2, Integer.parseInt(sid));
                    ins.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // ================= GET STUDENT BY ID =================
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id=?";
        Student s = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    public List<Student> getStudentsByTeacherEmail(String email) {

        List<Student> list = new ArrayList<>();

        String sql = """
            SELECT DISTINCT st.id, st.name, st.email
            FROM students st
            JOIN student_subjects ss ON st.id = ss.student_id
            JOIN subjects s ON ss.subject_id = s.id
            JOIN teachers t ON s.teacher_id = t.id
            WHERE t.email = ?
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setName(rs.getString("name"));
                st.setEmail(rs.getString("email"));

                list.add(st);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
 // Save or update marks
    public void saveMarks(int studentId, int subjectId, int marks, String grade) {

        String sql =
          "INSERT INTO marks (student_id, subject_id, marks, grade) " +
          "VALUES (?, ?, ?, ?) " +
          "ON DUPLICATE KEY UPDATE marks=?, grade=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setInt(3, marks);
            ps.setString(4, grade);

            ps.setInt(5, marks);
            ps.setString(6, grade);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String[]> getStudentMarks(int studentId) {

        List<String[]> list = new ArrayList<>();

        String sql =
          "SELECT s.subject_name, m.marks, m.grade " +
          "FROM marks m " +
          "JOIN subjects s ON m.subject_id = s.id " +
          "WHERE m.student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("subject_name"),
                    rs.getString("marks"),
                    rs.getString("grade")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Object[]> getStudentsWithSubjectsByTeacherEmail(String email) {

        List<Object[]> list = new ArrayList<>();

        String sql =
            "SELECT st.id AS sid, st.name, st.email, " +
            "sb.id AS subid, sb.subject_name " +
            "FROM students st " +
            "JOIN student_subjects ss ON st.id = ss.student_id " +
            "JOIN subjects sb ON ss.subject_id = sb.id " +
            "JOIN teachers t ON sb.teacher_id = t.id " +
            "WHERE t.email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));

                Subject sub = new Subject();
                sub.setId(rs.getInt("subid"));
                sub.setSubjectName(rs.getString("subject_name"));

                list.add(new Object[]{s, sub});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getMarks(int studentId, int subjectId) {

        String sql = "SELECT marks FROM marks WHERE student_id=? AND subject_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("marks");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Subject getSubjectById(int id) {

        String sql = "SELECT * FROM subjects WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setSubjectName(rs.getString("subject_name"));
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String[]> getStudentAttendance(int studentId) {

        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT s.subject_name, a.date, a.status " +
            "FROM attendance a " +
            "JOIN subjects s ON a.subject_id = s.id " +
            "WHERE a.student_id = ? " +
            "ORDER BY a.date DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[] {
                    rs.getString("subject_name"),
                    rs.getString("date"),
                    rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<String[]> getMarksheet(int studentId) {

        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT s.subject_name, m.marks, m.grade " +
            "FROM marks m " +
            "JOIN subjects s ON m.subject_id = s.id " +
            "WHERE m.student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("subject_name"),
                    rs.getString("marks"),
                    rs.getString("grade")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<String[]> getAttendanceSummary(int studentId) {

        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT s.subject_name, " +
            "COUNT(a.id) AS total_classes, " +
            "SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END) AS present_count " +
            "FROM attendance a " +
            "JOIN subjects s ON a.subject_id = s.id " +
            "WHERE a.student_id = ? " +
            "GROUP BY s.subject_name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int total = rs.getInt("total_classes");
                int present = rs.getInt("present_count");
                double percent = total == 0 ? 0 : (present * 100.0 / total);

                list.add(new String[] {
                    rs.getString("subject_name"),
                    String.valueOf(total),
                    String.valueOf(present),
                    String.format("%.2f", percent)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateStudent(Student s) {

        String sql = "UPDATE students SET name=?, email=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setInt(3, s.getId());

            return ps.executeUpdate() > 0; // true if updated

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteStudent(int id) {

        String deleteStudentSubjects = "DELETE FROM student_subjects WHERE student_id=?";
        String deleteMarks = "DELETE FROM marks WHERE student_id=?";
        String deleteStudent = "DELETE FROM students WHERE id=?";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false); // start transaction

            try (PreparedStatement ps1 = conn.prepareStatement(deleteStudentSubjects);
                 PreparedStatement ps2 = conn.prepareStatement(deleteMarks);
                 PreparedStatement ps3 = conn.prepareStatement(deleteStudent)) {

                ps1.setInt(1, id);
                ps1.executeUpdate();

                ps2.setInt(1, id);
                ps2.executeUpdate();

                ps3.setInt(1, id);
                ps3.executeUpdate();

                conn.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
