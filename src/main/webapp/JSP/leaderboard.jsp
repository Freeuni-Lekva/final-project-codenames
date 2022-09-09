<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.service.UserService" %>
<%@ page import="com.example.codenames.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.service.implementation.UserServiceImpl" %><%--
  Created by IntelliJ IDEA.
  User: ruska-ubuntu
  Date: 30.08.22
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>LeaderBoard</title>
</head>
<body>
    <div align="center">
    <h1>Leaderboard </h1> <br/>
    </div>

    <div align="center">
    <table border="1" cellpadding="5"  WIDTH=700>
        <tr>
            <th>Rating</th>
            <th>Username</th>
            <th>Points</th>
        </tr>
        <%
            ServletContext sc = request.getServletContext();
            UserServiceImpl service = (UserServiceImpl) sc.getAttribute(NameConstants.USER_SERVICE);
            List<User> users = service.getUsersByPoints(false);
            int i = 1;
            for(User user : users) {%>
            <tr>
                <td><%=i%></td>
                <td><%=user.getUsername()%></td>
                <td><%=user.getPoints()%></td>
            </tr>
            <% i++;
            } %>

    </table>
    </div>


</body>
</html>
