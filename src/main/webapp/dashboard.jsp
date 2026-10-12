<%@ page contentType="text/html;charset=UTF-8" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }

    String userName = (String) session.getAttribute("userName");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Smart Study Planner - Dashboard</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f4f6f8;
        }

        .header {
            background: #2c3e50;
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            margin: 0;
        }

        .logout {
            color: white;
            text-decoration: none;
            background: #e74c3c;
            padding: 10px 18px;
            border-radius: 6px;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 20px;
        }

        .welcome {
            background: white;
            padding: 25px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .welcome h2 {
            margin-top: 0;
        }

        .features {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .card {
            background: white;
            padding: 30px 20px;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .card h3 {
            margin-bottom: 10px;
        }

        .card p {
            color: #666;
        }

        .button {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .button:hover {
            background: #2980b9;
        }

        @media (max-width: 700px) {

            .features {
                grid-template-columns: 1fr;
            }

            .header {
                padding: 15px 20px;
            }

            .container {
                margin: 20px auto;
            }
        }

    </style>

</head>

<body>

<div class="header">

    <h1>Smart Study Planner</h1>

    <a class="logout"
       href="<%= request.getContextPath() %>/logout">
        Logout
    </a>

</div>


<div class="container">

    <div class="welcome">

        <h2>Welcome, <%= userName %>! 👋</h2>

        <p>
            Manage your subjects, tasks and study schedule
            from one place.
        </p>

    </div>


    <div class="features">

        <!-- Subjects -->

        <div class="card">

            <h3>📚 My Subjects</h3>

            <p>
                Add and manage your academic subjects.
            </p>

            <a class="button"
               href="<%= request.getContextPath() %>/subjects">
                Open Subjects
            </a>

        </div>


        <!-- Tasks -->

        <div class="card">

            <h3>📝 My Tasks</h3>

            <p>
                Create tasks, set priorities and track completion.
            </p>

            <a class="button"
               href="<%= request.getContextPath() %>/tasks">
                Open Tasks
            </a>

        </div>


        <!-- Schedule -->

        <div class="card">

            <h3>📅 Study Schedule</h3>

            <p>
                Plan your study sessions and manage your time.
            </p>

            <a class="button"
               href="<%= request.getContextPath() %>/schedule">
                Open Schedule
            </a>

        </div>

    </div>

</div>

</body>
</html>