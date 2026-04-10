package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentProfileDAO;
import model.Student;
import model.StudentProfile;

@WebServlet("/saveProfile")
public class StudentProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("studentInfo"); // we'll define this

        int studentId = student.getId();
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String className = request.getParameter("className");
        String dob = request.getParameter("dob");

        StudentProfile profile = new StudentProfile(studentId, phone, address, className, dob);

        StudentProfileDAO dao = new StudentProfileDAO();
        dao.saveOrUpdateProfile(profile);

        response.sendRedirect("student-profile.jsp?success=1");
    }
}
