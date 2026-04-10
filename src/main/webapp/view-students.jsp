<%@ page import="java.util.*" %>
<%@ page import="model.Student" %>
<%@ page import="dao.StudentDAO" %>

<%
    StudentDAO dao = new StudentDAO();
    List<Student> students = dao.getAllStudents();
%>

<h2>All Students</h2>

<table border="1" cellpadding="8">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Action</th>
    </tr>

<% for(Student s : students) { %>
    <tr>
        <td><%= s.getId() %></td>
        <td><%= s.getName() %></td>
        <td><%= s.getEmail() %></td>
        <td>
            <!-- EDIT -->
            <a href="edit-student.jsp?id=<%= s.getId() %>">Edit</a>
            |
            <!-- DELETE -->
            <a href="deleteStudent?id=<%= s.getId() %>"
               onclick="return confirm('Delete this student?');">
               Delete
            </a>
        </td>
    </tr>
<% } %>
</table>

<a href="admin-dashboard.jsp">Back</a>
