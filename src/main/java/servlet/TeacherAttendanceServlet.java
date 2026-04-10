package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.AttendanceDAO;
import dao.SubjectDAO;
import model.Subject;
import model.User;

@WebServlet("/teacher-attendance")
public class TeacherAttendanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null || !"TEACHER".equals(teacher.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        AttendanceDAO adao = new AttendanceDAO();
        SubjectDAO sdao = new SubjectDAO();

        // ✅ load subjects for dropdown
        List<Subject> subjects =
                sdao.getSubjectsByTeacherEmail(teacher.getUsername());

        // ✅ load attendance records
        List<String[]> records =
                adao.getAttendanceByTeacherEmail(teacher.getUsername());

        request.setAttribute("subjects", subjects);
        request.setAttribute("records", records);

        request.getRequestDispatcher("teacher-attendance.jsp")
               .forward(request, response);
    }
}
