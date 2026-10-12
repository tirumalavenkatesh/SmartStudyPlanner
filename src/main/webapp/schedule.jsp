
<%@ page import="java.util.List" %>
<%@ page import="com.smartstudy.model.Schedule" %>
<%@ page import="com.smartstudy.model.Subject" %>
<%@ page import="com.smartstudy.dao.SubjectDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userId");

    SubjectDAO subjectDAO = new SubjectDAO();
    List<Subject> subjects = subjectDAO.getSubjectsByUser(userId);

    List<Schedule> schedules =
            (List<Schedule>) request.getAttribute("schedules");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Study Schedule</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1000px;
            margin: auto;
        }

        h1 {
            color: #243b64;
        }

        .card {
            background: white;
            padding: 25px;
            margin: 20px 0;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        input, select, button {
            padding: 10px;
            margin: 8px 0;
            width: 100%;
            box-sizing: border-box;
        }

        button {
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background: #1d4ed8;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #2563eb;
            color: white;
        }

        .delete-btn {
            background: #dc2626;
        }

        .back {
            display: inline-block;
            margin: 10px 0;
            color: #2563eb;
            text-decoration: none;
        }

        .error {
            color: #dc2626;
        }
    </style>
</head>

<body>
<div class="container">

    <h1>My Study Schedule</h1>

    <a class="back"
       href="<%= request.getContextPath() %>/dashboard.jsp">
        ← Back to Dashboard
    </a>

    <div class="card">
        <h2>Add Study Session</h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>

        <form action="<%= request.getContextPath() %>/schedule"
              method="post">

            <input type="hidden" name="action" value="add">

            <label>Subject</label>
            <select name="subjectId" required>
                <option value="">Select Subject</option>

                <% for (Subject subject : subjects) { %>
                    <option value="<%= subject.getId() %>">
                        <%= subject.getSubjectName() %>
                    </option>
                <% } %>
            </select>

            <label>Topic</label>
            <input type="text" name="topic"
                   placeholder="Enter study topic" required>

            <label>Study Date</label>
            <input type="date" name="studyDate" required>

            <label>Start Time</label>
            <input type="time" name="startTime" required>

            <label>End Time</label>
            <input type="time" name="endTime" required>

            <button type="submit">Add Schedule</button>
        </form>
    </div>

    <div class="card">
        <h2>My Scheduled Sessions</h2>

        <table>
            <tr>
                <th>Subject ID</th>
                <th>Topic</th>
                <th>Date</th>
                <th>Start</th>
                <th>End</th>
                <th>Action</th>
            </tr>

            <% if (schedules != null && !schedules.isEmpty()) {
                for (Schedule schedule : schedules) {
            %>
                <tr>
                    <td><%= schedule.getSubjectId() %></td>
                    <td><%= schedule.getTopic() %></td>
                    <td><%= schedule.getStudyDate() %></td>
                    <td><%= schedule.getStartTime() %></td>
                    <td><%= schedule.getEndTime() %></td>
                    <td>
                        <form action="<%= request.getContextPath() %>/schedule"
                              method="post">

                            <input type="hidden" name="action"
                                   value="delete">

                            <input type="hidden" name="id"
                                   value="<%= schedule.getId() %>">

                            <button class="delete-btn"
                                    type="submit">
                                Delete
                            </button>
                        </form>
                    </td>
                </tr>
            <% }
            } else { %>
                <tr>
                    <td colspan="6">No study sessions scheduled yet.</td>
                </tr>
            <% } %>
        </table>
    </div>

</div>
</body>
</html>