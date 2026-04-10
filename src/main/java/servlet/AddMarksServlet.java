package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.StudentDAO;

@WebServlet("/add-marks")
public class AddMarksServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int marks = Integer.parseInt(request.getParameter("marks"));

        // ✅ AUTO GRADE LOGIC
        String grade;
        if (marks >= 90) grade = "A+";
        else if (marks >= 80) grade = "A";
        else if (marks >= 70) grade = "B";
        else if (marks >= 60) grade = "C";
        else if (marks >= 40) grade = "D";
        else grade = "F";

        StudentDAO dao = new StudentDAO();
        dao.saveMarks(studentId, subjectId, marks, grade);

        response.sendRedirect("teacher-dashboard.jsp");
    }
}
