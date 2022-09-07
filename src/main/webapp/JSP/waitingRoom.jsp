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
    <div id="button1" onclick="setRole('Red Spymaster')">
        <li style="color: red; font-size: 35px">Spymasters</li>
    </div>
    <div id="redSpymasters">

    </div>
    <%-- --%>
    <div id="button2" onclick="setRole('Red Operative')">
        <li style="color: red; font-size: 35px">Operatives</li>
    </div>
    <div id="redOperatives">

    </div>
</ul>
<ul class ="tableColumn">
    <li style="color: black; font-size: 35px"> Users </li>
    <div id="users">

    </div>
</ul>
<ul class ="tableColumn">
    <li style="color: blue; font-size: 35px">Blue Team</li>
    <div id="button3" onclick="setRole('Blue Spymaster')"> <li style="color: blue; font-size: 35px">Spymasters</li> </div>
    <div id="blueSpymasters">

    </div>
    <%-- --%>
    <div id="button4" onclick="setRole('Blue Operative')"> <li style="color: blue; font-size: 35px">Operatives</li> </div>
    <div id="blueOperatives">

    </div>
</ul>
<%--<script src="<%=request.getContextPath()%>/JSP/waitingRoom.js"> </script>--%>
</body>
</html>
<script>
    var socket = undefined
    var users = document.querySelector("#users");
    var redSpymasters = document.querySelector("#redSpymasters");
    var redOperatives = document.querySelector("#redOperatives");
    var blueSpymasters = document.querySelector("#blueSpymasters");
    var blueOperatives = document.querySelector("#blueOperatives");
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
        var type = e.data.substr(0, e.data.indexOf(" "));
        if (type == "NewUser" || type == "RemoveUser"){
            while (users.hasChildNodes()){
                users.removeChild(users.lastChild);
            }
            var data = JSON.parse(e.data.substr(e.data.indexOf(" ") + 1));
            var fragment = document.createDocumentFragment();
            for (var i=0; i<data.allPlayers.length; i++){
                var name = data.allPlayers[i].user.username;
                var li = document.createElement("li");
                li.id = name;
                li.textContent = name;
                li.style.color = "black";
                li.style.fontSize = "35px";
                fragment.appendChild(li);
            }
            users.appendChild(fragment);
            displayMembers(redSpymasters, e);
            displayMembers(redOperatives, e);
            displayMembers(blueSpymasters, e);
            displayMembers(blueOperatives, e);
        } else if (type == "AddUserRole") {
            displayMembers(redSpymasters, e);
            displayMembers(redOperatives, e);
            displayMembers(blueSpymasters, e);
            displayMembers(blueOperatives, e);
        }
    }

    function onClose(e){
        socket = undefined;
    }

    function setRole(role) {
        let socketMessage = {
            role
        }
        socket.send(JSON.stringify(socketMessage));
    }

    function displayMembers(role, e) {
        while (role.hasChildNodes()) {
            role.removeChild(role.lastChild);
        }
        var data = JSON.parse(e.data.substr(e.data.indexOf(" ") + 1));
        var fragment = document.createDocumentFragment();
        for (var i=0; i<data.allPlayers.length; i++){
            var name = data.allPlayers[i].user.username;
            var userRole = data.allPlayers[i].playerRole;
            var roleStr = roleToString(role);
            console.log(userRole + " " + roleStr);
            if (userRole !== roleStr) {
                console.log("not equal");
                continue;
            };
            var li = document.createElement("li");
            li.textContent = name;
            li.style.color = "black";
            li.style.fontSize = "35px";
            fragment.appendChild(li);
        }
        role.appendChild(fragment);
    }

    function roleToString(role){
        if (role === redSpymasters) {
            return "RED_SPYMASTER";
        } else if (role === redOperatives) {
            return "RED_OPERATIVE";
        } else if (role === blueSpymasters) {
            return "BLUE_SPYMASTER";
        } else if (role === blueOperatives) {
            return "BLUE_OPERATIVE";
        }
        return "NOT_SELECTED";
    }
</script>