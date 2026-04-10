package dao;

import java.sql.*;
import java.util.*;

import model.Teacher;
import model.Subject;      // ✅ REQUIRED
import util.DBUtil;

public class TeacherDAO {

    // ===== GET SUBJECTS BY TEACHER EMAIL =====
    public List<Subject> getSubjectsByTeacherEmail(String email) {

        List<Subject> list = new ArrayList<>();

        String sql =
            "SELECT s.id, s.subject_name " +
            "FROM subjects s " +
            "JOIN teachers t ON s.teacher_id = t.id " +
            "WHERE t.email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject sub = new Subject();
                sub.setId(rs.getInt("id"));
                sub.setSubjectName(rs.getString("subject_name"));
                list.add(sub);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    } // ✅ METHOD CLOSED CORRECTLY


    // ===== GET ALL TEACHERS =====
    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();

        String sql = "SELECT id, name, email FROM teachers";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Teacher t = new Teacher();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setEmail(rs.getString("email"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ===== GET BY ID =====
    public Teacher getTeacherById(int id) {

        String sql = "SELECT * FROM teachers WHERE id=?";
        Teacher t = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                t = new Teacher();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setEmail(rs.getString("email"));
                t.setSubject(rs.getString("subject"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    // ===== UPDATE =====
    public boolean updateTeacher(Teacher t) {

        String sql = "UPDATE teachers SET name=?, email=?, subject=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getSubject());
            ps.setInt(4, t.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== DELETE =====
  
    public boolean deleteTeacherWithSubjects(int teacherId) {

        String deleteStudentSubjects =
            "DELETE FROM student_subjects WHERE subject_id IN " +
            "(SELECT id FROM subjects WHERE teacher_id = ?)";

        String deleteSubjects =
            "DELETE FROM subjects WHERE teacher_id = ?";

        String deleteTeacher =
            "DELETE FROM teachers WHERE id = ?";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false); // start transaction

            try (
                PreparedStatement ps1 = conn.prepareStatement(deleteStudentSubjects);
                PreparedStatement ps2 = conn.prepareStatement(deleteSubjects);
                PreparedStatement ps3 = conn.prepareStatement(deleteTeacher)
            ) {
                // 1️⃣ delete student_subjects
                ps1.setInt(1, teacherId);
                ps1.executeUpdate();

                // 2️⃣ delete subjects
                ps2.setInt(1, teacherId);
                ps2.executeUpdate();

                // 3️⃣ delete teacher
                ps3.setInt(1, teacherId);
                ps3.executeUpdate();

                conn.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTeacher(Teacher t) {

        String teacherSQL =
            "INSERT INTO teachers (name, email) VALUES (?, ?)";

        String userSQL =
            "INSERT INTO users (username, password, role) VALUES (?, ?, 'TEACHER')";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement ps1 = conn.prepareStatement(teacherSQL);
                PreparedStatement ps2 = conn.prepareStatement(userSQL)
            ) {
                ps1.setString(1, t.getName());
                ps1.setString(2, t.getEmail());
                ps1.executeUpdate();

                ps2.setString(1, t.getEmail());
                ps2.setString(2, t.getPassword());
                ps2.executeUpdate();

                conn.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
