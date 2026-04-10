<%@ page import="java.util.*" %>
<%@ page import="model.Student" %>
<%@ page import="model.Subject" %>

<%
    Student student = (Student) session.getAttribute("studentData");
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
    List<String[]> attendance = (List<String[]>) request.getAttribute("attendance");

    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Dashboard</title>

<style>
body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f4f6f9;
}

.topbar {
    background: #1e88e5;
    padding: 15px 30px;
    color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.container {
    padding: 30px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
    gap: 20px;
}

.card {
    background: white;
    padding: 20px;
    border-radius: 6px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.2);
}

.card h3 {
    margin-top: 0;
    color: #1e88e5;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    text-align: center;
}

th {
    background: #1e88e5;
    color: white;
}

.logout-btn {
    color: white;
    text-decoration: none;
    font-weight: bold;
}

.empty {
    color: gray;
}
</style>
</head>

<body>

<!-- TOP BAR -->
<div class="topbar">
    <div>🎓 Student Dashboard</div>
    <div>
        <b><%= student.getName() %></b> |
        <a href="logout" class="logout-btn">Logout</a>
    </div>
</div>
<a href="<%= request.getContextPath() %>/marksheet-pdf"
   style="padding:10px 15px;background:#1e88e5;color:white;text-decoration:none;border-radius:4px;">
   📄 Download Marksheet
</a>

<!-- MAIN CONTENT -->
<div class="container">

    <!-- PROFILE -->
    <div class="card">
        <h3>👤 My Profile</h3>
        <p><b>Name:</b> <%= student.getName() %></p>
        <p><b>Email:</b> <%= student.getEmail() %></p>
    </div>

    <!-- SUBJECTS -->
    <div class="card">
        <h3>📚 My Subjects</h3>

        <% if (subjects == null || subjects.isEmpty()) { %>
            <p class="empty">No subjects assigned</p>
        <% } else { %>
            <ul>
                <% for (Subject s : subjects) { %>
                    <li><%= s.getSubjectName() %></li>
                <% } %>
            </ul>
        <% } %>
    </div>

    <!-- ATTENDANCE -->
    <div class="card">
        <h3>📅 Attendance</h3>

        <table>
            <tr>
                <th>Subject</th>
                <th>Date</th>
                <th>Status</th>
            </tr>

            <% if (attendance == null || attendance.isEmpty()) { %>
                <tr>
                    <td colspan="3">No attendance records</td>
                </tr>
            <% } else {
                for (String[] a : attendance) { %>
                <tr>
                    <td><%= a[0] %></td>
                    <td><%= a[1] %></td>
                    <td style="color:<%= a[2].equals("P") ? "green" : "red" %>">
                        <%= a[2].equals("P") ? "Present" : "Absent" %>
                    </td>
                </tr>
            <% } } %>
        </table>
    </div>

</div>
<a href="<%= request.getContextPath() %>/student-attendance">
    📊 Open My Attendance
</a>

</body>
</html>
