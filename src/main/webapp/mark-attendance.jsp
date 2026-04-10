<%@ page import="java.util.*" %>
<%@ page import="model.Subject" %>
<%@ page import="dao.SubjectDAO" %>
<%@ page import="model.User" %>

<%
User teacher = (User) session.getAttribute("loggedUser");
if (teacher == null || !"TEACHER".equals(teacher.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}

int studentId = Integer.parseInt(request.getParameter("studentId"));

SubjectDAO dao = new SubjectDAO();
List<Subject> subjects = dao.getSubjectsByTeacherEmail(teacher.getUsername());
%>

<html>
<head>
<title>Mark Attendance</title>
</head>
<body>

<h2>Mark Attendance</h2>

<form action="save-attendance" method="post">

<input type="hidden" name="studentId" value="<%= studentId %>">

Date:
<input type="date" name="date" required><br><br>

Subject:
<select name="subjectId" required>
<% for(Subject s : subjects) { %>
    <option value="<%= s.getId() %>"><%= s.getSubjectName() %></option>
<% } %>
</select>
<br><br>

Status:
<select name="status">
    <option value="P">Present</option>
    <option value="A">Absent</option>
</select>
<br><br>

<button type="submit">Save Attendance</button>

</form>

</body>
</html>
