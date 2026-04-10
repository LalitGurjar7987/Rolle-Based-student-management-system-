package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

import dao.SubjectDAO;
import dao.TeacherDAO;
import model.Subject;
import model.Teacher;

@WebServlet("/assign-teacher-subjects")
public class AssignTeacherSubjectsServlet extends HttpServlet {

    // Show page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        SubjectDAO sdao = new SubjectDAO();
        TeacherDAO tdao = new TeacherDAO();

        List<Subject> allSubjects = sdao.getAllSubjects();
        Set<Integer> assigned = sdao.getSubjectIdsByTeacher(teacherId);
        Teacher teacher = tdao.getTeacherById(teacherId);

        request.setAttribute("subjects", allSubjects);
        request.setAttribute("assigned", assigned);
        request.setAttribute("teacher", teacher);

        request.getRequestDispatcher("assign-teacher-subjects.jsp")
               .forward(request, response);
    }

    // Save
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        String[] subjectIds = request.getParameterValues("subjects");

        SubjectDAO dao = new SubjectDAO();
        dao.assignSubjectsToTeacher(teacherId, subjectIds);

        response.sendRedirect("view-teachers.jsp");
    }
}
