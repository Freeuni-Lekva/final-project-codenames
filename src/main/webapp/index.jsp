<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.model.User" %>
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
<%
    if(session.getAttribute(User.ATTRIBUTE) != null){
        response.sendRedirect("JSP/userPage.jsp");
    }
%>
<body>
<h1>Welcome to Codenames! </h1> <br/>
<p> Please log in: </p>
<form action="LoginServlet" method="post">
    <div class="form__group field">
        <input type="input" class="form__field" placeholder="Username" name="Username" id='Username' required />
        <label for="Username" class="form__label">Name</label>
    </div>
    <div class="form__group field">
        <input type="password" class="form__field" placeholder="Password" name="Password" id='Password' required />
        <label for="Password" class="form__label">Password</label>
    </div>
    <br>
    <button class="button_login" role="button">Login</button>
</form>
<p> If you do not have an account, please <a href="JSP/register.jsp">register.</a> </p>

</body>
</html>