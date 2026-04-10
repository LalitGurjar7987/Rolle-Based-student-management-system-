package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.UserDAO;
import dao.StudentDAO;
import model.User;
import model.Student;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");  // email for students
        String password = request.getParameter("password");

        System.out.println("👉 LoginServlet triggered");
        System.out.println("Username = " + username);

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);

        if (user == null) {
            System.out.println("❌ Login FAILED: Invalid credentials");
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ✔ User found
        System.out.println("✔ Login SUCCESS for: " + user.getUsername());

        HttpSession session = request.getSession();
        session.setAttribute("loggedUser", user);

        // ========================
        // ROLE-BASED REDIRECT
        // ========================
        switch (user.getRole()) {

            case "ADMIN":
                response.sendRedirect("admin-dashboard.jsp");
                break;

            case "TEACHER":
            	response.sendRedirect(request.getContextPath() + "/teacher-dashboard");
                break;

            case "STUDENT":
                StudentDAO sdao = new StudentDAO();
                Student student = sdao.getStudentByEmail(user.getUsername());

                if (student == null) {
                    request.setAttribute("error", "Student record not found");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                // ⭐⭐⭐ THIS WAS MISSING ⭐⭐⭐
                session.setAttribute("studentData", student);

                response.sendRedirect(request.getContextPath() + "/student-dashboard");
                break;



            default:
                System.out.println("⚠ Unknown role. Redirecting to login.");
                response.sendRedirect("login.jsp");
        }
    }
}
