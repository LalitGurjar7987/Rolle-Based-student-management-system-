package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.SubjectDAO;
import model.Subject;
import model.User;

@WebServlet("/teacher-subjects")
public class TeacherSubjectsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User teacher = (User) session.getAttribute("loggedUser");

        SubjectDAO dao = new SubjectDAO();
        List<Subject> subjects =
                dao.getSubjectsByTeacherEmail(teacher.getUsername());

        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("teacher-subjects.jsp")
               .forward(request, response);
    }
}
