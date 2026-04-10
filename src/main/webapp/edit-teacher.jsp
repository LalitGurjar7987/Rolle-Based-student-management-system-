<%@ page import="model.User" %>
<%@ page import="dao.TeacherDAO" %>
<%@ page import="model.Teacher" %>

<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idStr = request.getParameter("id");
    if (idStr == null) {
        response.sendRedirect("view-teachers.jsp");
        return;
    }

    int id = Integer.parseInt(idStr);
    TeacherDAO dao = new TeacherDAO();
    Teacher t = dao.getTeacherById(id);

    if (t == null) {
        response.sendRedirect("view-teachers.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Teacher</title>
</head>

<body>

<h2>Edit Teacher</h2>

<form action="<%= request.getContextPath() %>/updateTeacher" method="post">
    <input type="hidden" name="id" value="<%= t.getId() %>">

    Name:<br>
    <input type="text" name="name" value="<%= t.getName() %>" required><br><br>

    Email:<br>
    <input type="email" name="email" value="<%= t.getEmail() %>" required><br><br>

    Subject:<br>
    <input type="text" name="subject" value="<%= t.getSubject() %>" required><br><br>

    <button type="submit">Update</button>
</form>

<br>
<a href="view-teachers.jsp">← Back</a>

</body>
</html>
