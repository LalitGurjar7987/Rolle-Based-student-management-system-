<%@ page import="java.util.*" %>
<%@ page import="model.User" %>
<%@ page import="model.Student" %>
<%@ page import="model.Subject" %>
<%@ page import="dao.StudentDAO" %>
<%@ page import="dao.TeacherDAO" %>

<%
    // ===== SECURITY =====
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"TEACHER".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    TeacherDAO tdao = new TeacherDAO();
    StudentDAO sdao = new StudentDAO();

    List<Subject> subjects = tdao.getSubjectsByTeacherEmail(loggedUser.getUsername());
    List<Student> students = sdao.getAllStudents(); // simple version
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Marks</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<h2>➕ Add Marks</h2>

<form action="<%= request.getContextPath() %>/add-marks" method="post">

    <!-- STUDENT -->
    <label>Student:</label><br>
    <select name="studentId" required>
        <option value="">-- Select Student --</option>
        <% for (Student s : students) { %>
            <option value="<%= s.getId() %>"><%= s.getName() %></option>
        <% } %>
    </select>
    <br><br>

    <!-- SUBJECT -->
    <label>Subject:</label><br>
    <select name="subjectId" required>
        <option value="">-- Select Subject --</option>
        <% for (Subject sub : subjects) { %>
            <option value="<%= sub.getId() %>"><%= sub.getSubjectName() %></option>
        <% } %>
    </select>
    <br><br>

    <!-- MARKS -->
    <label>Marks:</label><br>
    <input type="number" name="marks" min="0" max="100" required>
    <br><br>

    <button type="submit">Save Marks</button>
</form>

<br>
<a href="teacher-dashboard.jsp">← Back to Dashboard</a>

</body>
</html>
