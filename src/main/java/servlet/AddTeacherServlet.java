package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.TeacherDAO;
import model.Teacher;

@WebServlet("/addTeacher")
public class AddTeacherServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Teacher t = new Teacher();
        t.setName(name);
        t.setEmail(email);
        t.setPassword(password);

        TeacherDAO dao = new TeacherDAO();
        boolean success = dao.addTeacher(t);

        if (success) {
            response.sendRedirect("view-teachers.jsp");
        } else {
            request.setAttribute("error", "Teacher already exists");
            request.getRequestDispatcher("add-teacher.jsp").forward(request, response);
        }
    }
}
