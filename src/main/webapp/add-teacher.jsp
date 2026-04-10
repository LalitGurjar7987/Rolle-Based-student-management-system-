<%@ page import="model.User" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Add Teacher</title></head>
<body>
<h2>Add New Teacher</h2>
<form action="<%= request.getContextPath() %>/addTeacher" method="post">

    Name:
    <input type="text" name="name" required><br><br>

    Email:
    <input type="email" name="email" required><br><br>

    Password:
    <input type="password" name="password" required><br><br>

    <button type="submit">Add Teacher</button>
</form>

<a href="admin-dashboard.jsp">Back</a>
</body>
</html>
