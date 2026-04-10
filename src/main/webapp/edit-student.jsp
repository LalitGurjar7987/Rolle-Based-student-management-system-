<%@ page import="dao.StudentDAO" %>
<%@ page import="model.Student" %>

<%
    // Role protection
    model.User loggedUser = (model.User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    int id = Integer.parseInt(request.getParameter("id"));
    StudentDAO dao = new StudentDAO();
    Student student = dao.getStudentById(id);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Student</title>

<style>
    body {
        font-family: Arial;
        background: #f4f6f9;
        padding: 30px;
    }

    .form-box {
        width: 400px;
        margin: auto;
        background: white;
        padding: 25px;
        border-radius: 5px;
    }

    input, button {
        width: 100%;
        padding: 10px;
        margin-top: 10px;
    }

    button {
        background: #1e88e5;
        color: white;
        border: none;
        cursor: pointer;
    }
</style>
</head>

<body>

<div class="form-box">
    <h2>Edit Student</h2>

    <form action="updateStudent" method="post">
        <input type="hidden" name="id" value="<%= student.getId() %>">

        <label>Name</label>
        <input type="text" name="name" value="<%= student.getName() %>" required>

        <label>Email</label>
        <input type="email" name="email" value="<%= student.getEmail() %>" required>

        <label>Password</label>
        <input type="text" name="password" value="<%= student.getPassword() %>" required>

        <button type="submit">Update Student</button>
    </form>
</div>

</body>
</html>
