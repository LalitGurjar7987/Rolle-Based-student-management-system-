package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.SubjectDAO;
import dao.TeacherDAO;
import model.Subject;
import model.Teacher;
import model.User;

@WebServlet("/viewTeacherSubjects")
public class ViewTeacherSubjectsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loggedUser = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        TeacherDAO tdao = new TeacherDAO();
        SubjectDAO sdao = new SubjectDAO();

        Teacher teacher = tdao.getTeacherById(teacherId);
        List<Subject> subjects = sdao.getSubjectsByTeacherId(teacherId);

        request.setAttribute("teacher", teacher);
        request.setAttribute("subjects", subjects);

        request.getRequestDispatcher("view-teacher-subjects.jsp")
               .forward(request, response);
    }
}
