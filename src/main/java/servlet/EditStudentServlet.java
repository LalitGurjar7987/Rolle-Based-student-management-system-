package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentDAO;
import model.Student;

@WebServlet("/edit-student")
public class EditStudentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        model.User user = (model.User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        StudentDAO dao = new StudentDAO();
        Student student = dao.getStudentById(id);

        request.setAttribute("student", student);
        request.getRequestDispatcher("edit-student.jsp").forward(request, response);
    }
}
