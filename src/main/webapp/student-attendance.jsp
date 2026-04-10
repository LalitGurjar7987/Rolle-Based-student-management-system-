<%@ page import="java.util.*" %>

<%
    List<String[]> records =
        (List<String[]>) request.getAttribute("records");

    int totalCount =
        request.getAttribute("totalCount") == null ? 0 :
        (int) request.getAttribute("totalCount");

    int presentCount =
        request.getAttribute("presentCount") == null ? 0 :
        (int) request.getAttribute("presentCount");

    double percentage =
        request.getAttribute("percentage") == null ? 0 :
        (double) request.getAttribute("percentage");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Attendance</title>

<style>
body {
    font-family: Arial;
    background: #f4f6f9;
    padding: 30px;
}
.card {
    background: white;
    padding: 25px;
    max-width: 800px;
    margin: auto;
    box-shadow: 0 2px 8px rgba(0,0,0,0.2);
}
.summary {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
    font-weight: bold;
}
table {
    width: 100%;
    border-collapse: collapse;
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
.present { color: green; font-weight: bold; }
.absent { color: red; font-weight: bold; }
.btn {
    display: inline-block;
    margin-top: 20px;
    padding: 8px 14px;
    background: #1e88e5;
    color: white;
    text-decoration: none;
}
</style>
</head>

<body>

<div class="card">

    <h2 style="text-align:center;">📊 My Attendance</h2>

    <div class="summary">
        <div>Total Classes: <%= totalCount %></div>
        <div>Present: <%= presentCount %></div>
        <div>Attendance: <%= String.format("%.2f", percentage) %>%</div>
    </div>

    <table>
        <tr>
            <th>Subject</th>
            <th>Date</th>
            <th>Status</th>
        </tr>

        <% if (records == null || records.isEmpty()) { %>
            <tr>
                <td colspan="3">No attendance records</td>
            </tr>
        <% } else {
            for (String[] r : records) {
                boolean present =
                    "P".equalsIgnoreCase(r[2]) ||
                    "PRESENT".equalsIgnoreCase(r[2]);
        %>
        <tr>
            <td><%= r[0] %></td>
            <td><%= r[1] %></td>
            <td class="<%= present ? "present" : "absent" %>">
                <%= present ? "PRESENT" : "ABSENT" %>
            </td>
        </tr>
        <% }} %>
    </table>

    <a href="student-dashboard" class="btn">← Back to Dashboard</a>

</div>

</body>
</html>
