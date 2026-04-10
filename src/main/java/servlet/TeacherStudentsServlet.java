package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.StudentDAO;
import dao.SubjectDAO;
import model.Student;
import model.User;

@WebServlet("/teacher-students")
public class TeacherStudentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User teacher = (User) session.getAttribute("loggedUser");

        StudentDAO dao = new StudentDAO();

        // Each row = Object[]{Student, Subject}
        List<Object[]> rows = dao.getStudentsWithSubjectsByTeacherEmail(
                teacher.getUsername()
        );

        request.setAttribute("rows", rows);
        request.getRequestDispatcher("teacher-students.jsp")
               .forward(request, response);
    }
}
