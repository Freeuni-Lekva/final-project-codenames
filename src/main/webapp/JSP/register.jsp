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
<%--    User Name: <input type="text" name="Username"> <br>--%>
    <div class="form__group field">
        <input type="input" class="form__field" placeholder="Username" name="Username" id='Username' required />
        <label for="Username" class="form__label">Name</label>
    </div>
    <div class="form__group field">
        <input type="password" class="form__field" placeholder="Password" name="Password" id='Password' required />
        <label for="Password" class="form__label">Password</label>
    </div>
    <br>
    <button class="button_register" role="button">Register</button>
</form>
</body>
</html>
