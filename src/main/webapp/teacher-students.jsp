<%@ page import="java.util.*" %>
<%@ page import="model.Student" %>
<%@ page import="model.Subject" %>

<%
    List<Object[]> rows = (List<Object[]>) request.getAttribute("rows");
%>

<h2>My Students</h2>

<table border="1" cellpadding="10">
<tr>
    <th>Student</th>
    <th>Subject</th>
    <th>Action</th>
</tr>

<% if (rows != null) {
   for (Object[] row : rows) {
       Student st = (Student) row[0];
       Subject sub = (Subject) row[1];
%>
<tr>
    <td><%= st.getName() %></td>
    <td><%= sub.getSubjectName() %></td>
    <td>
   <a href="mark-attendance.jsp?studentId=<%= st.getId() %>">Mark Attendance</a>
</td>
    
    <td>
        <a href="teacher-add-marks.jsp?studentId=<%= st.getId() %>&subjectId=<%= sub.getId() %>">
            ➕ Add Marks
        </a>
    </td>
    <td>
    <a href="edit-marks.jsp?studentId=<%= st.getId() %>&subjectId=<%= sub.getId() %>">
        Edit Marks
    </a>
</td>
    
</tr>
<% }} %>
</table>
