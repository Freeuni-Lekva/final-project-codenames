<%@ page import="com.example.codenames.listener.NameConstants" %><%--
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
    <div class="form__group field">
        <input type="input" class="form__field" placeholder="<%=NameConstants.ROOM_ID%>" name="<%=NameConstants.ROOM_ID%>" id='<%=NameConstants.ROOM_ID%>' required />
        <label for="<%=NameConstants.ROOM_ID%>" class="form__label">Name</label>
    </div>
<%--    <h2>Room ID: <input type="text" name="<%=NameConstants.ROOM_ID%>">--%>
<%--    <input type="submit" name="Join" value="Join"></h2>--%><br>
    <button class="button_join" role="button">Login</button>

</form>
</body>
</html>
