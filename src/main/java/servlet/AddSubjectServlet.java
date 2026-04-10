package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import dao.SubjectDAO;

@WebServlet("/add-subject")
public class AddSubjectServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String subject = request.getParameter("subject");

        SubjectDAO dao = new SubjectDAO();
        dao.addSubject(subject);

        response.sendRedirect("view-subjects.jsp");
    }
}
