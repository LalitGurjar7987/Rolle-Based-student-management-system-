package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentDAO;
import model.Student;

@WebServlet("/updateStudent")
public class UpdateStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setEmail(email);
        s.setPassword(password);

        StudentDAO dao = new StudentDAO();
        dao.updateStudent(s);

        response.sendRedirect("view-students");
    }
}
