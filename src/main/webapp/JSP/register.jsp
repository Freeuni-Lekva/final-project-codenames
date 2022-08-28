<%--
  Created by IntelliJ IDEA.
  com.example.codenames.Model.User: keti
  Date: 16.07.22
  Time: 02:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="forRegister.css">
<head>
    <title>Register</title>
</head>
<body>
<h1>Please Register! </h1> <br/>
<form action="../RegistrationServlet" method="post">
    User Name: <input type="text" name="Username"> <br>
    Password:&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="Password"> <br>
    <input type="submit" name="Register" value="Register">
</form>
</body>
</html>
