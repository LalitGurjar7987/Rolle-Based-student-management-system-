<%@ page import="dao.StudentDAO" %>
<%@ page import="model.Student" %>
<%@ page import="model.Subject" %>

<%
    int studentId = Integer.parseInt(request.getParameter("studentId"));
    int subjectId = Integer.parseInt(request.getParameter("subjectId"));

    StudentDAO dao = new StudentDAO();

    Student student = dao.getStudentById(studentId);
    Subject subject = dao.getSubjectById(subjectId);

    int marks = dao.getMarks(studentId, subjectId); // existing marks
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Marks</title>
</head>

<body>

<h2>Edit Marks</h2>

<p><b>Student:</b> <%= student.getName() %></p>
<p><b>Subject:</b> <%= subject.getSubjectName() %></p>

<form action="add-marks" method="post">

    <input type="hidden" name="studentId" value="<%= studentId %>">
    <input type="hidden" name="subjectId" value="<%= subjectId %>">

    Marks:
    <input type="number" name="marks" value="<%= marks %>" min="0" max="100" required>

    <br><br>
    <button type="submit">Update Marks</button>
</form>

<a href="teacher-students">← Back</a>

</body>
</html>
