<%@ page import="model.User" %>
<%@ page import="model.Student" %>

<%
    // BLOCK unauthorized access
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"STUDENT".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Load session student data
    Student student = (Student) session.getAttribute("studentData");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>

    <style>
        body {
            background: #f4f6f9;
            font-family: Arial;
            padding: 20px;
        }

        .container {
            width: 420px;
            background: white;
            padding: 25px;
            margin: auto;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 12px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        button {
            width: 100%;
            padding: 12px;
            background: #43a047;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
        }

        a.back {
            display: block;
            margin-top: 15px;
            text-align: center;
            text-decoration: none;
            color: #43a047;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>My Profile</h2>

    <form action="updateStudent" method="post">

        <!-- Hidden field for student ID -->
        <input type="hidden" name="id" value="<%= student.getId() %>">

        <label>Name:</label>
        <input type="text" name="name" value="<%= student.getName() %>" required>

        <label>Email:</label>
        <input type="email" name="email" value="<%= student.getEmail() %>" required>

        <label>Password:</label>
        <input type="password" name="password" value="<%= student.getPassword() %>" required>

        <button type="submit">Save Changes</button>

    </form>

    <a href="student-dashboard.jsp" class="back">⬅ Back to Dashboard</a>

</div>

</body>
</html>
