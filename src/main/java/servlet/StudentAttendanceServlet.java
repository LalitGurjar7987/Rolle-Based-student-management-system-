package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.AttendanceDAO;
import model.Student;

@WebServlet("/student-attendance")
public class StudentAttendanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("studentData");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        AttendanceDAO dao = new AttendanceDAO();
        List<String[]> records = dao.getAttendanceByStudent(student.getId());

        int totalCount = records.size();
        int presentCount = 0;

        for (String[] r : records) {
            String status = r[2]; // P / A
            if ("P".equalsIgnoreCase(status) || "PRESENT".equalsIgnoreCase(status)) {
                presentCount++;
            }
        }

        double percentage =
                totalCount == 0 ? 0 : (presentCount * 100.0 / totalCount);

        request.setAttribute("records", records);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("presentCount", presentCount);
        request.setAttribute("percentage", percentage);

        request.getRequestDispatcher("student-attendance.jsp")
               .forward(request, response);
    }
}
