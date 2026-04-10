package dao;

import java.sql.*;
import model.StudentProfile;
import util.DBUtil;

public class StudentProfileDAO {

    public StudentProfile getProfileByStudentId(int studentId) {
        String sql = "SELECT * FROM student_profile WHERE student_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                StudentProfile profile = new StudentProfile();
                profile.setId(rs.getInt("id"));
                profile.setStudentId(rs.getInt("student_id"));
                profile.setPhone(rs.getString("phone"));
                profile.setAddress(rs.getString("address"));
                profile.setClassName(rs.getString("class_name"));
                profile.setDob(rs.getString("dob"));
                return profile;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean saveOrUpdateProfile(StudentProfile profile) {
        String checkSql = "SELECT id FROM student_profile WHERE student_id=?";
        String updateSql = "UPDATE student_profile SET phone=?, address=?, class_name=?, dob=? WHERE student_id=?";
        String insertSql = "INSERT INTO student_profile (student_id, phone, address, class_name, dob) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection()) {

            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, profile.getStudentId());
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Update existing
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.setString(1, profile.getPhone());
                ps.setString(2, profile.getAddress());
                ps.setString(3, profile.getClassName());
                ps.setString(4, profile.getDob());
                ps.setInt(5, profile.getStudentId());
                return ps.executeUpdate() > 0;
            } else {
                // Insert new
                PreparedStatement ps = conn.prepareStatement(insertSql);
                ps.setInt(1, profile.getStudentId());
                ps.setString(2, profile.getPhone());
                ps.setString(3, profile.getAddress());
                ps.setString(4, profile.getClassName());
                ps.setString(5, profile.getDob());
                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
