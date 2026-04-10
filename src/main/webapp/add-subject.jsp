<%@ page import="model.User" %>

<%
User u = (User) session.getAttribute("loggedUser");
if (u == null || !"ADMIN".equals(u.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<h2>Add Subject</h2>

<form action="<%= request.getContextPath() %>/add-subject" method="post">
    Subject Name:
    <input type="text" name="subject" required>
    <br><br>
    <button type="submit">Add Subject</button>
</form>

<a href="admin-dashboard.jsp">Back</a>
