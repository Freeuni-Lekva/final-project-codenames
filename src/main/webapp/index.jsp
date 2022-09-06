<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="JSP/forIndex.css">
<head>
    <% String er = (String) (request.getSession()).getAttribute(NameConstants.LOGIN_ERROR);
        if (er != null) {
            out.print(er);
        }
    %>
    <title>Codenames</title>
</head>
<body>
<h1>Welcome to Codenames! </h1> <br/>
<p> Please log in: </p>
<form action="LoginServlet" method="post">
    User Name: <input type="text" name="Username"> <br>
    Password:&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="Password"> <br>
    <input type="submit" name="Login" value="Login">
</form>
<p> If you do not have an account, please <a href="JSP/register.jsp">register.</a> </p>

</body>
</html>