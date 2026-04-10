<%@ page import="model.Student" %>
<%
    Student student = (Student) session.getAttribute("studentData");

    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <style>
        body { font-family: Arial; background: #f4f6f9; padding: 20px; }
        .form-box {
            background: white; padding: 25px; width: 350px; margin: auto;
            border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        input { width: 100%; padding: 10px; margin-bottom: 12px;
            border-radius: 5px; border: 1px solid #ccc; }
        button {
            width: 100%; padding: 12px; background: #1e88e5;
            border: none; border-radius: 5px; color: white; font-size: 16px;
        }
        a { display: block; margin-top: 15px; text-align: center;
            color: #1e88e5; text-decoration: none; }
    </style>
</head>

<body>

<h2 style="text-align:center;">Edit Profile</h2>

<div class="form-box">

    <form action="updateStudent" method="post">
        
        <input type="hidden" name="id" value="<%= student.getId() %>">

        <label>Name:</label>
        <input type="text" name="name" value="<%= student.getName() %>" required>

        <label>Email:</label>
        <input type="email" name="email" value="<%= student.getEmail() %>" required>

        <label>Password:</label>
        <input type="password" name="password" value="<%= student.getPassword() %>" required>

        <button type="submit">Update Profile</button>
    </form>

    <a href="student-dashboard.jsp">⬅ Back to Dashboard</a>

</div>

</body>
</html>
