<%@ page import="com.smartstudy.dao.SubjectDAO" %>
<%@ page import="com.smartstudy.model.Subject" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = (int) session.getAttribute("userId");

    SubjectDAO subjectDAO = new SubjectDAO();
  List<Subject> subjects = subjectDAO.getSubjectsByUser(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Subjects - Smart Study Planner</title>
</head>

<body>

<h1>My Subjects</h1>

<h3>Add New Subject</h3>

<form action="subjects" method="post">

    <input type="hidden" name="action" value="add">

    <label>Subject Name:</label>
    <input type="text" name="subjectName" required>

    <br><br>

    <label>Description:</label>
    <input type="text" name="description">

    <br><br>

    <button type="submit">Add Subject</button>

</form>

<hr>

<h3>My Subjects</h3>

<%
    if (subjects.isEmpty()) {
%>

    <p>No subjects added yet.</p>

<%
    } else {

        for (Subject subject : subjects) {
%>

    <div>

        <strong>
            <%= subject.getSubjectName() %>
        </strong>

        <br>

        <%= subject.getDescription() %>

        <br><br>

        <!-- Delete Subject -->
        <form action="subjects" method="post">

            <input type="hidden" name="action" value="delete">

            <input type="hidden"
                   name="subjectId"
                   value="<%= subject.getId() %>">

            <button type="submit">Delete</button>

        </form>

        <hr>

    </div>

<%
        }
    }
%>

<br>

<a href="dashboard.jsp">Back to Dashboard</a>

</body>
</html>