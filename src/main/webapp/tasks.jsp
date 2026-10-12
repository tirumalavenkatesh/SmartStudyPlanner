<%@ page import="com.smartstudy.dao.TaskDAO" %>
<%@ page import="com.smartstudy.dao.SubjectDAO" %>
<%@ page import="com.smartstudy.model.Task" %>
<%@ page import="com.smartstudy.model.Subject" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int userId = (int) session.getAttribute("userId");

   List<Subject> subjects =
           (List<Subject>) request.getAttribute("subjects");

   List<Task> tasks =
           (List<Task>) request.getAttribute("tasks");
%>

<!DOCTYPE html>
<html>

<head>
    <title>My Tasks - Smart Study Planner</title>
</head>

<body>

<h1>My Tasks</h1>

<h3>Add New Task</h3>

<%
    if (subjects.isEmpty()) {
%>

    <p>Please add a subject first before creating a task.</p>

    <a href="subjects.jsp">Go to My Subjects</a>

<%
    } else {
%>

<form action="tasks" method="post">

    <input type="hidden" name="action" value="add">

    <label>Subject:</label>

    <select name="subjectId" required>

        <option value="">-- Select Subject --</option>

        <%
            for (Subject subject : subjects) {
        %>

        <option value="<%= subject.getId() %>">
            <%= subject.getSubjectName() %>
        </option>

        <%
            }
        %>

    </select>

    <br><br>

    <label>Task Title:</label>
    <input type="text" name="title" required>

    <br><br>

    <label>Description:</label>
    <textarea name="description"></textarea>

    <br><br>

    <label>Priority:</label>

    <select name="priority" required>

        <option value="">-- Select Priority --</option>
        <option value="High">High</option>
        <option value="Medium">Medium</option>
        <option value="Low">Low</option>

    </select>

    <br><br>

    <label>Due Date:</label>
    <input type="date" name="dueDate" required>

    <br><br>

    <button type="submit">Add Task</button>

</form>

<%
    }
%>

<hr>

<h3>My Tasks</h3>

<%
    if (tasks.isEmpty()) {
%>

    <p>No tasks added yet.</p>

<%
    } else {

        for (Task task : tasks) {
%>

<div>

    <strong>
        <%= task.getTitle() %>
    </strong>

    <br>

    Description:
    <%= task.getDescription() %>

    <br>

    Priority:
    <%= task.getPriority() %>

    <br>

    Due Date:
    <%= task.getDueDate() %>

    <br>

    Status:
    <strong>
        <%= task.getStatus() %>
    </strong>

    <br><br>

    <!-- Mark as Completed -->
    <%
        if ("Pending".equals(task.getStatus())) {
    %>

    <form action="tasks" method="post">

        <input type="hidden"
               name="action"
               value="updateStatus">

        <input type="hidden"
               name="taskId"
               value="<%= task.getId() %>">

        <input type="hidden"
               name="status"
               value="Completed">

        <button type="submit">
            Mark as Completed
        </button>

    </form>

    <%
        } else {
    %>

        <p>✓ Task Completed</p>

    <%
        }
    %>

    <!-- Delete Task -->
    <form action="tasks" method="post">

        <input type="hidden"
               name="action"
               value="delete">

        <input type="hidden"
               name="taskId"
               value="<%= task.getId() %>">

        <button type="submit">
            Delete
        </button>

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