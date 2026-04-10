package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentDAO;
import model.Student;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setPassword(password);

        StudentDAO dao = new StudentDAO();
        dao.addStudent(s);

        response.sendRedirect("view-students.jsp");
    }
}
