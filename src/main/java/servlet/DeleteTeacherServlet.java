package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.TeacherDAO;
import model.User;

@WebServlet("/deleteTeacher")
public class DeleteTeacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== SECURITY =====
        HttpSession session = request.getSession(false);
        User loggedUser = (session != null)
                ? (User) session.getAttribute("loggedUser")
                : null;

        if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ===== GET ID =====
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("view-teachers.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);

        // ===== DELETE =====
        TeacherDAO dao = new TeacherDAO();
        dao.deleteTeacherWithSubjects(id);
        response.sendRedirect("view-teachers.jsp");

    }
}
