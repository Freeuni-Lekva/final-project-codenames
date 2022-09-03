<%@ page import="com.example.codenames.listener.NameConstants" %><%--
  Created by IntelliJ IDEA.
  User: keti
  Date: 01.09.22
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/forWaitingRoom.css">
<head>
    <title>Waiting Room</title>
</head>
<body>
<h1>Waiting Room </h1> <br/>
<h2>id is
<script>
    var room = JSON.parse('${JSON}');
    document.write(room.ID);
</script>
</h2>
<ul class ="tableColumn">
    <li style="color: red; font-size: 35px">Red Team</li>
    <li style="color: red; font-size: 35px">Spymasters</li>
    <%-- --%>
    <li style="color: red; font-size: 35px">Operatives</li>
</ul>
<ul class ="tableColumn">
    <li style="color: black; font-size: 35px"> Users </li>
</ul>
<ul class ="tableColumn">
    <li style="color: blue; font-size: 35px">Blue Team</li>
    <li style="color: blue; font-size: 35px">Spymasters</li>
    <%-- --%>
    <li style="color: blue; font-size: 35px">Operatives</li>
</ul>
</body>
</html>
