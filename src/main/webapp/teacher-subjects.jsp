<%@ page import="java.util.List" %>
<%@ page import="model.Subject" %>

<%
    List<Subject> subjects =
        (List<Subject>) request.getAttribute("subjects");
%>

<!DOCTYPE html>
<html>
<head>
<title>My Subjects</title>
</head>
<body>

<h2>My Subjects</h2>

<ul>
<% for (Subject s : subjects) { %>
    <li><%= s.getSubjectName() %></li>
<% } %>
</ul>

<a href="teacher-dashboard.jsp">← Back</a>

</body>
</html>
