<%@ page import="java.util.*" %>
<%@ page import="model.Subject" %>
<%@ page import="model.Teacher" %>

<%
    Teacher teacher = (Teacher) request.getAttribute("teacher");
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
    Set<Integer> assigned = (Set<Integer>) request.getAttribute("assigned");
%>

<h2>Assign Subjects to <%= teacher.getName() %></h2>

<form method="post" action="<%= request.getContextPath() %>/assign-teacher-subjects">

    <input type="hidden" name="teacherId" value="<%= teacher.getId() %>">

    <% for (Subject s : subjects) { %>
        <label>
            <input type="checkbox"
                   name="subjects"
                   value="<%= s.getId() %>"
                   <%= assigned.contains(s.getId()) ? "checked" : "" %>>
            <%= s.getSubjectName() %>
        </label><br>
    <% } %>

    <br>
    <button type="submit">Save</button>
</form>

<a href="view-teachers.jsp">← Back</a>
