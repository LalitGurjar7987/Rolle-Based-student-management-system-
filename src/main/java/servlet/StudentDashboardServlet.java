package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.StudentDAO;
import model.Student;
import model.Subject;

@WebServlet("/student-dashboard")
public class StudentDashboardServlet extends HttpServlet {

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

        // 1️⃣ Student subjects
        List<Subject> subjects = dao.getSubjectsForStudent(student.getId());

        // 2️⃣ Student marks
        List<String[]> marks = dao.getStudentMarks(student.getId());
        List<String[]> attendance = dao.getStudentAttendance(student.getId());
        request.setAttribute("attendance", attendance);

        request.setAttribute("student", student);
        request.setAttribute("subjects", subjects);
        request.setAttribute("marks", marks);

        // 4️⃣ ONE forward (VERY IMPORTANT)
        request.getRequestDispatcher("student-dashboard.jsp")
               .forward(request, response);
    }
}
