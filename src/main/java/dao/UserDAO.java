package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.DBUtil;

public class UserDAO {

    public User login(String username, String password) {

        User user = null;

        try {
            Connection conn = DBUtil.getConnection();
            System.out.println("Connection = " + conn);

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            System.out.println("SQL: " + sql);

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            System.out.println("Executing query for: " + username + " / " + password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("✔ USER FOUND: " + rs.getString("username"));

                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            } else {
                System.out.println("❌ NO USER FOUND for: " + username + " / " + password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
