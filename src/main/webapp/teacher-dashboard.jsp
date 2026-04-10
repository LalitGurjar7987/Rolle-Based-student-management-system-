<%@ page import="model.User" %>

<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"TEACHER".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Dashboard</title>

<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: #f4f6f9;
    }

    .sidebar {
        width: 220px;
        height: 100vh;
        background: #2c3e50;
        color: white;
        position: fixed;
        padding-top: 20px;
    }

    .sidebar h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .sidebar a {
        display: block;
        padding: 12px 20px;
        color: white;
        text-decoration: none;
    }

    .sidebar a:hover {
        background: #34495e;
    }

    .content {
        margin-left: 220px;
        padding: 30px;
    }

    .card {
        background: white;
        padding: 20px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.2);
        border-radius: 6px;
        width: 400px;
    }

    .logout {
        color: #ff5252;
        font-weight: bold;
    }
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <h2>Teacher Panel</h2>

    <a href="<%= request.getContextPath() %>/teacher-dashboard">Dashboard</a>
    <a href="<%= request.getContextPath() %>/teacher-subjects">My Subjects</a>
    <a href="<%= request.getContextPath() %>/teacher-students">My Students</a>
    <a href="<%= request.getContextPath() %>/teacher-attendance">My Attendance</a>
    <a href="<%= request.getContextPath() %>/logout" class="logout">Logout</a>
</div>



<!-- CONTENT -->
<div class="content">
    <div class="card">
        <h2>Welcome, <%= loggedUser.getUsername() %></h2>
        <p>You are logged in as <b>Teacher</b>.</p>
        <p>Use the menu to manage your subjects and students.</p>
    </div>
</div>

</body>
</html>