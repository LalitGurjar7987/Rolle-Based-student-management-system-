package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import dao.StudentDAO;
import dao.SubjectDAO;
@WebServlet("/assignSubjects")
public class AssignSubjectsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String teacherId = request.getParameter("teacherId");
        String studentId = request.getParameter("studentId");
        String[] subjectIds = request.getParameterValues("subjects");

        SubjectDAO sdao = new SubjectDAO();
        StudentDAO stdao = new StudentDAO();

        if (teacherId != null) {
            sdao.assignSubjectsToTeacher(Integer.parseInt(teacherId), subjectIds);
            response.sendRedirect("view-teachers.jsp");
            return;
        }

        if (studentId != null) {
            stdao.saveStudentSubjects(Integer.parseInt(studentId), subjectIds);
            response.sendRedirect("view-students.jsp");
        }
    }
}
