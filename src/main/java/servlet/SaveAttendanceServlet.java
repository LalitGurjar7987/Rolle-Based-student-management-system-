package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import util.DBUtil;
import model.User;

@WebServlet("/save-attendance")
public class SaveAttendanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== Security =====
        HttpSession session = request.getSession(false);
        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null || !"TEACHER".equals(teacher.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        String sql = "INSERT INTO attendance (student_id, subject_id, date, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setDate(3, java.sql.Date.valueOf(date));
            ps.setString(4, status);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("teacher-students.jsp");
    }
}
