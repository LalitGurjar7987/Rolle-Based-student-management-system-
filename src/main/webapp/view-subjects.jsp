<%@ page import="java.util.*" %>
<%@ page import="dao.SubjectDAO" %>
<%@ page import="model.Subject" %>

<%
SubjectDAO dao = new SubjectDAO();
List<Subject> list = dao.getAllSubjects();
%>

<h2>All Subjects</h2>
<a href="add-subject.jsp">➕ Add Subject</a>

<table border="1">
<tr><th>ID</th><th>Subject</th><th>Teacher</th></tr>

<% for (Subject s : list) { %>
<tr>
    <td><%= s.getId() %></td>
    <td><%= s.getSubjectName() %></td>
    <td><%= s.getTeacherId() == 0 ? "Unassigned" : s.getTeacherId() %></td>
</tr>
<% } %>

</table>

<a href="admin-dashboard.jsp">Back</a>
