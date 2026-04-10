<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Student Management System</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: white;
            padding: 25px;
            width: 360px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.2);
            border-radius: 6px;
        }

        h2 {
            text-align: center;
            margin-bottom: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: 100%;
            padding: 10px;
            background: #1e88e5;
            color: white;
            border: none;
            border-radius: 4px;
            margin-top: 15px;
            cursor: pointer;
        }

        button:hover {
            background: #1565c0;
        }

        .hint {
            font-size: 13px;
            background: #f1f1f1;
            padding: 10px;
            margin-top: 15px;
            border-radius: 4px;
        }

        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="login-box">
    <h2>Login</h2>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <input type="text" name="username" placeholder="Username / Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>

    <!-- LOGIN HINTS -->
    <div class="hint">
        <b>Login Guide:</b><br>
        🔹 <b>Admin</b> → use <code>admin</code> username<br>
        🔹 <b>Teacher</b> → use teacher username/email<br>
        🔹 <b>Student</b> → use student <b>email</b><br>
        <small>(Use password assigned by admin)</small>
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error">Invalid username or password</div>
    <% } %>
</div>

</body>
</html>
