<%--
  Created by IntelliJ IDEA.
  User: keti
  Date: 04.09.22
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/forJoinRoom.css">
<head>
    <title>Join Room</title>
</head>
<body>
<h1>Enter ID below to join room</h1>
<form action="../JoinRoomServlet" method="post">
    <h2>Room ID: <input type="text" name="roomID">
    <input type="submit" name="Join" value="Join"></h2>
</form>
</body>
</html>
