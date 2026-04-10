package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.StudentDAO;
import model.Student;

@WebServlet("/marksheet")
public class MarksheetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("studentData");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        StudentDAO dao = new StudentDAO();
        List<String[]> marksheet = dao.getMarksheet(student.getId());

        request.setAttribute("marksheet", marksheet);
        request.setAttribute("student", student);

        request.getRequestDispatcher("marksheet.jsp")
               .forward(request, response);
    }
}
