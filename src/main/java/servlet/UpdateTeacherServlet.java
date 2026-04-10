package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import dao.TeacherDAO;
import model.Teacher;

@WebServlet("/updateTeacher")
public class UpdateTeacherServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");

        Teacher t = new Teacher();
        t.setId(id);
        t.setName(name);
        t.setEmail(email);
        t.setSubject(subject);

        TeacherDAO dao = new TeacherDAO();
        dao.updateTeacher(t);

        response.sendRedirect("view-teachers.jsp");
    }
}
