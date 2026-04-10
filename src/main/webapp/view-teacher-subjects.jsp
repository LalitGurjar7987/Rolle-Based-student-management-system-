<%@ page import="java.util.*" %>
<%@ page import="model.Subject" %>
<%@ page import="dao.SubjectDAO" %>
<%@ page import="model.User" %>

<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idStr = request.getParameter("id");   // ✅ FIX HERE
    if (idStr == null) {
        out.println("Teacher ID missing");
        return;
    }

    int teacherId = Integer.parseInt(idStr);

    SubjectDAO dao = new SubjectDAO();
    List<Subject> subjects = dao.getSubjectsByTeacherId(teacherId);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Subjects</title>
</head>

<body>

<h2>📘 Assigned Subjects</h2>

<% if (subjects == null || subjects.isEmpty()) { %>
    <p>No subjects assigned yet.</p>
<% } else { %>
    <ul>
        <% for (Subject s : subjects) { %>
            <li><%= s.getSubjectName() %></li>
        <% } %>
    </ul>
<% } %>

<br>
<a href="view-teachers.jsp">← Back to Teachers</a>

</body>
</html>
