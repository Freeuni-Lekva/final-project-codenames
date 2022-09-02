<%@ page import="com.example.codenames.model.User" %>
<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<style><%@include file="/JSP/forRegister.css"%></style>
<head>
    <% String er = (String) (request.getSession()).getAttribute(NameConstants.REGISTRATION_ERROR);
        if (er != null) {
            out.print(er);
        }
    %>
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
