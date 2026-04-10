package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.TeacherDAO;
import model.Subject;
import model.User;

@WebServlet("/teacher-dashboard")
public class TeacherDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User teacher = (User) session.getAttribute("loggedUser");

        TeacherDAO dao = new TeacherDAO();
        List<Subject> subjects =
                dao.getSubjectsByTeacherEmail(teacher.getUsername());

        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("teacher-dashboard.jsp")
               .forward(request, response);
    }
}
