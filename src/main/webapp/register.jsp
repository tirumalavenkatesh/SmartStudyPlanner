<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
</head>
<body>

<h2>Smart Study Planner - Registration</h2>

<form action="register" method="post">

    <label>Name:</label>
    <input type="text" name="name" required>
    <br><br>

    <label>Email:</label>
    <input type="email" name="email" required>
    <br><br>

    <label>Password:</label>
    <input type="password" name="password" required>
    <br><br>

    <label>College:</label>
    <input type="text" name="college">
    <br><br>

    <label>Branch:</label>
    <input type="text" name="branch">
    <br><br>

    <label>Year:</label>
    <input type="number" name="year" min="1" max="5">
    <br><br>

    <button type="submit">Register</button>

</form>

<br>

<a href="login.jsp">Already have an account? Login</a>

</body>
</html>