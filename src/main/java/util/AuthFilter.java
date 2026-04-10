package util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURI();
        HttpSession session = req.getSession(false);

        System.out.println("FILTER URL = " + url);

        // ===== PUBLIC =====
        if (url.endsWith("login.jsp")
                || url.endsWith("/login")
                || url.contains("/css")
                || url.contains("/js")
                || url.contains("/images")) {

            chain.doFilter(request, response);
            return;
        }

        // ===== SESSION CHECK =====
        if (session == null || session.getAttribute("loggedUser") == null) {
            System.out.println("❌ No session or loggedUser");
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        String role = user.getRole();

        System.out.println("ROLE = " + role);

        // ===== DASHBOARD RULES ONLY =====
        if (url.endsWith("admin-dashboard.jsp") && !"ADMIN".equals(role)) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if (url.endsWith("teacher-dashboard.jsp") && !"TEACHER".equals(role)) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if (url.endsWith("/student-dashboard") && !"STUDENT".equals(role)) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}
