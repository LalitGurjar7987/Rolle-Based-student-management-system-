package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentDAO;
import model.Student;

@WebServlet("/enroll-subjects")
public class EnrollSubjectsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("studentData");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String[] subjectIds = request.getParameterValues("subjects");

        StudentDAO dao = new StudentDAO();
        dao.saveStudentSubjects(student.getId(), subjectIds);

        response.sendRedirect("student-dashboard.jsp");
    }
}
