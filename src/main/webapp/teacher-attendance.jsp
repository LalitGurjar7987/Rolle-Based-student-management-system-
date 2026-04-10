<%@ page import="java.util.*" %>
<%@ page import="model.Subject" %>

<h2>📋 Attendance Records</h2>

<form method="get" action="teacher-attendance">

    Subject:
    <select name="subjectId">
        <option value="">All</option>

        <%
            List<Subject> subjects =
                (List<Subject>) request.getAttribute("subjects");

            if (subjects != null) {
                for (Subject s : subjects) {
        %>
            <option value="<%= s.getId() %>">
                <%= s.getSubjectName() %>
            </option>
        <%
                }
            }
        %>
    </select>

    Date:
    <input type="date" name="date">

    <button type="submit">Filter</button>
</form>

<br>

<table border="1" cellpadding="8">
    <tr>
        <th>Student</th>
        <th>Subject</th>
        <th>Date</th>
        <th>Status</th>
    </tr>

<%
    List<String[]> rows =
        (List<String[]>) request.getAttribute("records");

    if (rows == null || rows.isEmpty()) {
%>
    <tr><td colspan="4">No records</td></tr>
<%
    } else {
        for (String[] r : rows) {
%>
    <tr>
        <td><%= r[0] %></td>
        <td><%= r[1] %></td>
        <td><%= r[2] %></td>
        <td style="color:<%= "PRESENT".equalsIgnoreCase(r[3]) ? "green" : "red" %>;">
            <%= r[3] %>
        </td>
    </tr>
<%
        }
    }
%>
</table>

<br>
<a href="teacher-dashboard.jsp">← Back</a>
