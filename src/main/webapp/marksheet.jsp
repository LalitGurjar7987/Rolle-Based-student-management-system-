<%@ page import="java.util.*" %>
<%@ page import="model.Student" %>

<%
    // ================= AUTH =================
    Student student = (Student) session.getAttribute("studentData");
    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // ================= DATA =================
    List<String[]> marksheet =
        (List<String[]>) request.getAttribute("marksheet");

    int total = 0;
    int count = 0;
    double percentage = 0;
    boolean overallPass = true;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Marksheet</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f6f9;
        padding: 30px;
    }

    .sheet {
        background: white;
        padding: 25px;
        width: 650px;
        margin: auto;
        box-shadow: 0 2px 8px rgba(0,0,0,0.2);
        border-radius: 8px;
    }

    h2, h3 {
        text-align: center;
        margin-bottom: 10px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    th, td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: center;
    }

    th {
        background: #1e88e5;
        color: white;
    }

    .pass {
        color: green;
        font-weight: bold;
    }

    .fail {
        color: red;
        font-weight: bold;
    }

    .summary {
        margin-top: 20px;
        font-weight: bold;
        line-height: 1.6;
    }

    .btn {
        display: block;
        margin: 25px auto 0;
        padding: 10px 18px;
        background: #1e88e5;
        color: white;
        text-decoration: none;
        text-align: center;
        width: 140px;
        border-radius: 4px;
    }

    .btn:hover {
        background: #1565c0;
    }
</style>
</head>

<body>

<div class="sheet">

    <h2>🎓 Student Marksheet</h2>

    <h3>
        <%= student.getName() %><br>
        <small><%= student.getEmail() %></small>
    </h3>

    <table>
        <tr>
            <th>Subject</th>
            <th>Marks</th>
            <th>Grade</th>
            <th>Result</th>
        </tr>

        <% if (marksheet == null || marksheet.isEmpty()) { %>
            <tr>
                <td colspan="4">No marks available</td>
            </tr>
        <% } else {
            for (String[] m : marksheet) {
                int marks = Integer.parseInt(m[1]);
                String grade = m[2];
                String subjectResult = marks >= 40 ? "PASS" : "FAIL";

                total += marks;
                count++;

                if ("FAIL".equals(subjectResult)) {
                    overallPass = false;
                }
        %>

        <tr>
            <td><%= m[0] %></td>
            <td><%= marks %></td>
            <td><%= grade %></td>
            <td class="<%= subjectResult.equals("PASS") ? "pass" : "fail" %>">
                <%= subjectResult %>
            </td>
        </tr>

        <% } 
            percentage = (double) total / count;
        } %>
    </table>

    <div class="summary">
        Total Marks: <%= total %><br>
        Percentage: <%= String.format("%.2f", percentage) %>%<br>
        Overall Result:
        <span class="<%= overallPass ? "pass" : "fail" %>">
            <%= overallPass ? "PASS" : "FAIL" %>
        </span>
    </div>

    <a href="student-dashboard" class="btn">⬅ Back</a>

</div>

</body>
</html>
