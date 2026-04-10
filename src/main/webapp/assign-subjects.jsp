<%@ page import="java.util.*" %>
<%@ page import="model.Subject" %>
<%@ page import="dao.SubjectDAO" %>
<%@ page import="dao.TeacherDAO" %>
<%@ page import="model.Teacher" %>
<%@ page import="model.User" %>

<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null || !"ADMIN".equals(loggedUser.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    int teacherId = Integer.parseInt(request.getParameter("teacherId"));

    SubjectDAO sdao = new SubjectDAO();
    TeacherDAO tdao = new TeacherDAO();

    List<Subject> subjects = sdao.getAllSubjects();
    Set<Integer> assigned = sdao.getSubjectIdsByTeacher(teacherId);
%>

<!DOCTYPE html>
<html>
<head>
<title>Assign Subjects to Teacher</title>
</head>

<body>

<h2>Assign Subjects</h2>

<form action="<%= request.getContextPath() %>/assignSubjects" method="post">

    <input type="hidden" name="teacherId" value="<%= teacherId %>">

    <% for (Subject s : subjects) { %>
        <label>
            <input type="checkbox" name="subjects"
                   value="<%= s.getId() %>"
                   <%= assigned.contains(s.getId()) ? "checked" : "" %>>
            <%= s.getSubjectName() %>
        </label><br>
    <% } %>

    <br>
    <button type="submit">Save</button>
</form>

<br>
<a href="view-teachers.jsp">← Back</a>

</body>
</html>
