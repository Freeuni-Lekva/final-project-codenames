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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<head>
    <title>Waiting Room</title>
</head>
<body>
<h1>Waiting Room </h1> <br/>
<h2>id is
    <script>
        var rooom = JSON.parse('${JSON}');
        document.write(rooom.ID);
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
    <div id="users">

    </div>
</ul>
<ul class ="tableColumn">
    <li style="color: blue; font-size: 35px">Blue Team</li>
    <li style="color: blue; font-size: 35px">Spymasters</li>
    <%-- --%>
    <li style="color: blue; font-size: 35px">Operatives</li>
</ul>
<%--<script src="<%=request.getContextPath()%>/JSP/waitingRoom.js"> </script>--%>
</body>
</html>
<script>
    var socket = undefined
    var users = document.querySelector("#users");
    window.onload = () => {
        var room = JSON.parse('${JSON}');
        socket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/room?roomID="+room.ID);
        socket.onopen = onOpen;
        socket.onmessage = onMessage;
        socket.onclose = onClose;
    }

    function onOpen(e){
        console.log("opened");
        const message = e.data;
        var roomObject = JSON.parse('${JSON}');
        var owner = roomObject.owner.user.username;
        var li = document.createElement("li");
        li.id = owner;
        li.textContent = owner;
        li.style.color = "black";
        li.style.fontSize = "35px";
        var fragment = document.createDocumentFragment();
        fragment.appendChild(li);
        users.appendChild(fragment);
    }

    function onMessage(e){
        const message = e.data;
        while(users.hasChildNodes()){
            users.removeChild(users.lastChild);
        }
        var type = e.data.substr(0, e.data.indexOf(" "));
        if(type == "NewUser" || type == "RemoveUser"){
            if(type == "RemoveUser"){
                console.log("sdfmjssdf");
            }
            var data = JSON.parse(e.data.substr(e.data.indexOf(" ") + 1));
            var fragment = document.createDocumentFragment();
            for(var i=0; i<data.allPlayers.length; i++){
                var name = data.allPlayers[i].user.username;
                console.log(name + ' is name');
                var li = document.createElement("li");
                li.id = name;
                li.textContent = name;
                li.style.color = "black";
                li.style.fontSize = "35px";
                fragment.appendChild(li);
            }
            users.appendChild(fragment);
        }
    }

    function onClose(e){
        socket = undefined;
    }
</script>