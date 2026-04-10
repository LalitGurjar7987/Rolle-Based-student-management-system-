package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import model.Subject;
import util.DBUtil;

public class SubjectDAO {

    // ===============================
    // GET SUBJECTS FOR A TEACHER (LOGIN)
    // ===============================
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

    // ===============================
    // GET ALL SUBJECTS (ADMIN)
    // ===============================
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

    // ===============================
    // GET SUBJECT IDS BY TEACHER (ADMIN UI)
    // ===============================
    public Set<Integer> getSubjectIdsByTeacher(int teacherId) {

        Set<Integer> set = new HashSet<>();
        String sql = "SELECT id FROM subjects WHERE teacher_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                set.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return set;
    }

    // ===============================
    // ASSIGN SUBJECTS TO TEACHER
    // ===============================
    public void assignSubjectsToTeacher(int teacherId, String[] subjectIds) {

        String clear = "UPDATE subjects SET teacher_id=NULL WHERE teacher_id=?";
        String assign = "UPDATE subjects SET teacher_id=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false);

            // clear old assignments
            try (PreparedStatement ps1 = conn.prepareStatement(clear)) {
                ps1.setInt(1, teacherId);
                ps1.executeUpdate();
            }

            // assign new ones
            if (subjectIds != null) {
                try (PreparedStatement ps2 = conn.prepareStatement(assign)) {
                    for (String sid : subjectIds) {
                        ps2.setInt(1, teacherId);
                        ps2.setInt(2, Integer.parseInt(sid));
                        ps2.executeUpdate();
                    }
                }
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Subject> getSubjectsByTeacherId(int teacherId) {

        List<Subject> list = new ArrayList<>();

        String sql = "SELECT * FROM subjects WHERE teacher_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
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
    public void addSubject(String subjectName) {

        String sql = "INSERT INTO subjects(subject_name) VALUES(?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, subjectName);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
