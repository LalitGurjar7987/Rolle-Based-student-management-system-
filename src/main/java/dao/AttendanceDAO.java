package dao;

import java.sql.*;
import java.util.*;
import util.DBUtil;

public class AttendanceDAO {

    public List<String[]> getAttendanceByStudent(int studentId) {

        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT s.subject_name, a.date, a.status " +
            "FROM attendance a " +
            "JOIN subjects s ON a.subject_id = s.id " +
            "WHERE a.student_id = ? " +
            "ORDER BY a.date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("subject_name"), // index 0
                    rs.getString("date"),         // index 1
                    rs.getString("status")        // index 2 (P / A)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<String[]> getAttendanceByTeacherEmail(String email) {

        List<String[]> list = new ArrayList<>();

        String sql =
            "SELECT st.name, s.subject_name, a.date, a.status " +
            "FROM attendance a " +
            "JOIN students st ON a.student_id = st.id " +
            "JOIN subjects s ON a.subject_id = s.id " +
            "JOIN teachers t ON s.teacher_id = t.id " +
            "WHERE t.email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("name"),
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
 
}
