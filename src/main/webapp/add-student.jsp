<%@ page import="model.User" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Student</title>

    <style>
        body {
            font-family: Arial;
            background: #f4f6f9;
            padding: 20px;
        }

        .form-box {
            background: white;
            padding: 25px;
            width: 350px;
            margin: auto;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
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
            background: #1e88e5;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
        }

        a.back-btn {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #1e88e5;
            text-decoration: none;
        }
    </style>
</head>

<body>

<h2 style="text-align:center;">Add New Student</h2>

<div class="form-box">

    <form action="addStudent" method="post">

        <input type="text" name="name" placeholder="Student Name" required>

        <input type="email" name="email" placeholder="Email" required>

        <input type="password" name="password" placeholder="Password" required>

        <button type="submit">Add Student</button>

    </form>

<a href="admin-dashboard.jsp" class="back-btn">← Back to Dashboard</a>

</div>

</body>
</html>
