<%@ page contentType="text/html;charset=UTF-8" %>
<%
    model.User loggedUser = (model.User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="layout">

    <!-- SIDEBAR -->
   <div class="sidebar">
    <h2>Admin Panel</h2>

    <a href="admin-dashboard.jsp">Dashboard</a>

    <hr>

    <a href="add-student.jsp">➕ Add Student</a>
    <a href="view-students.jsp">👨‍🎓 View Students</a>

    <hr>

    <a href="add-teacher.jsp">➕ Add Teacher</a>
    <a href="view-teachers.jsp">👩‍🏫 View Teachers</a>

    <hr>

    <a href="add-subject.jsp">➕ Add Subject</a>
    <a href="view-subjects.jsp">📚 View Subjects</a>

    <hr>

    <a href="logout" class="logout">Logout</a>
</div>

    <!-- MAIN CONTENT -->
    <div class="content">
        <h1>Welcome, Admin</h1>
        <p>Manage students, teachers, and subjects from here.</p>

        <div class="cards">
            <div class="card">👨‍🎓 Students</div>
            <div class="card">👩‍🏫 Teachers</div>
            <div class="card">📚 Subjects</div>
        </div>
    </div>

</div>

</body>
</html>
