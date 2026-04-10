<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Teacher" %>
<%@ page import="dao.TeacherDAO" %>

<%
    // ===== SECURITY =====
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    TeacherDAO dao = new TeacherDAO();
    List<Teacher> teachers = dao.getAllTeachers();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Teachers</title>

<style>
    body {
        font-family: Arial;
        background: #f4f6f9;
        padding: 30px;
    }

    h2 {
        text-align: center;
    }

    table {
        width: 90%;
        margin: auto;
        border-collapse: collapse;
        background: white;
        box-shadow: 0 2px 8px rgba(0,0,0,0.2);
    }

    th, td {
        padding: 12px;
        border-bottom: 1px solid #ddd;
        text-align: center;
    }

    th {
        background: #1e88e5;
        color: white;
    }

    tr:hover {
        background: #f1f1f1;
    }

    .actions a {
        margin: 0 5px;
        text-decoration: none;
        color: #1e88e5;
        font-weight: bold;
    }

    .actions a.delete {
        color: red;
    }

    .top-actions {
        width: 90%;
        margin: 0 auto 15px;
        display: flex;
        justify-content: space-between;
    }

    .btn {
        padding: 8px 14px;
        background: #1e88e5;
        color: white;
        text-decoration: none;
        border-radius: 4px;
    }

    .btn.back {
        background: #555;
    }

    .empty {
        text-align: center;
        padding: 20px;
        font-weight: bold;
        color: #777;
    }
</style>
</head>

<body>

<h2>👩‍🏫 Teachers List</h2>

<div class="top-actions">
    <a href="add-teacher.jsp" class="btn">➕ Add Teacher</a>
    <a href="admin-dashboard.jsp" class="btn back">← Dashboard</a>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Subject</th>
        <th>Actions</th>
    </tr>

    <% if (teachers == null || teachers.isEmpty()) { %>
        <tr>
            <td colspan="5" class="empty">No teachers found</td>
        </tr>
    <% } else {
        for (Teacher t : teachers) {
    %>
        <tr>
            <td><%= t.getId() %></td>
            <td><%= t.getName() %></td>
            <td><%= t.getEmail() %></td>
            <td>
                <%= (t.getSubject() != null && !t.getSubject().isEmpty())
                        ? t.getSubject()
                        : "—" %>
            </td>
            <td class="actions">
                <td>
    <a href="<%= request.getContextPath() %>/assign-teacher-subjects?teacherId=<%= t.getId() %>">
        Assign Subjects
    </a> |
    <a href="edit-teacher.jsp?id=<%= t.getId() %>">Edit</a> |
    <a href="deleteTeacher?id=<%= t.getId() %>"
       onclick="return confirm('Delete this teacher?');">Delete</a>
</td>

            </td>
        </tr>
    <%  } } %>
</table>

</body>
</html>
