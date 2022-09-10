<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.model.User" %>
<%@ page import="com.example.codenames.model.Room" %>
<%@ page import="java.util.Map" %>
<%@ page import="static com.example.codenames.listener.NameConstants.ROOM_ID" %>
<%@ page import="java.io.IOException" %><%--
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
    <style>
        .button_start_game {
            appearance: none;
            background-color: #FFFFFF;
            border-radius: 40em;
            border-style: none;
            box-shadow: #ADCFFF 0 -12px 6px inset;
            box-sizing: border-box;
            color: #000000;
            cursor: pointer;
            display: inline-block;
            font-family: -apple-system,sans-serif;
            font-size: 1.2rem;
            font-weight: 700;
            letter-spacing: -.24px;
            margin-left: 46%;
            outline: none;
            padding: 1rem 1.3rem;
            quotes: auto;
            text-align: center;
            text-decoration: none;
            transition: all .15s;
            user-select: none;
            -webkit-user-select: none;
            touch-action: manipulation;
        }

        .button_start_game:hover {
            background-color: #FFC229;
            box-shadow: #FF6314 0 -6px 8px inset;
            transform: scale(1.125);
        }

        .button_start_game:active {
            transform: scale(1.025);
        }

        @media (min-width: 768px) {
            .button_submit {
            font-size: 1.5rem;
            padding: .75rem 2rem;
            }
        }
    </style>

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
<%
    User user = (User) session.getAttribute(User.ATTRIBUTE);
    Map<String, Room> roomMap = (Map<String, Room>) application.getAttribute(NameConstants.ROOM_MAP);
    Room room = roomMap.getOrDefault(request.getParameter(ROOM_ID), null);
    String ownerUsername = room.getOwner().getUser().getUsername();
    if(room.getOwner().getUser().equals(user)){

%>
<button id="start-game" class="button_start_game" role="button" disabled onclick="startGame()">Start Game</button>
<input type="hidden" name="<%=ROOM_ID%>" value="<%=request.getParameter(ROOM_ID)%>">
<%
    }
%>

<%!
    public String redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("GameplayServlet?" + ROOM_ID + "=" + request.getParameter(ROOM_ID));
        return "";
    }
%>
</body>
</html>
<script>
    var socket = undefined;
    var startGameSocket = undefined;
    const artifact = "Codenames_war_exploded";
    var users = document.querySelector("#users");
    var redSpymasters = document.querySelector("#redSpymasters");
    var redOperatives = document.querySelector("#redOperatives");
    var blueSpymasters = document.querySelector("#blueSpymasters");
    var blueOperatives = document.querySelector("#blueOperatives");

    window.onload = () => {
        var room = JSON.parse('${JSON}');
        socket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/room?roomID="+room.ID);
        startGameSocket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/start-game?roomID="+room.ID);
        startGameSocket.onopen = undefined;
        startGameSocket.onmessage = onMessageStartGameSocket;
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
        var data = JSON.parse(e.data.substr(e.data.indexOf(" ") + 1));
        if (type == "NewUser" || type == "RemoveUser"){
            while (users.hasChildNodes()){
                users.removeChild(users.lastChild);
            }
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
        var rs = displayMembers(redSpymasters, e);
        var ro = displayMembers(redOperatives, e);
        var bs = displayMembers(blueSpymasters, e);
        var bo = displayMembers(blueOperatives, e);
        if(data.owner.user.username === "<%=user.getUsername()%>"){
            var startButton = document.getElementById("start-game");
            if (rs && ro && bs && bo) {
                startButton.disabled = false;
            } else {
                startButton.disabled = true;
            }
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
        var cnt = 0;
        for (var i=0; i<data.allPlayers.length; i++){
            var name = data.allPlayers[i].user.username;
            var userRole = data.allPlayers[i].playerRole;
            var roleStr = roleToString(role);
            console.log(userRole + " " + roleStr);
            if (userRole !== roleStr) {
                console.log("not equal");
                continue;
            }
            cnt++;
            var li = document.createElement("li");
            li.textContent = name;
            li.style.color = "black";
            li.style.fontSize = "35px";
            fragment.appendChild(li);
        }
        role.appendChild(fragment);
        return cnt > 0;
    }

    function roleToString(role) {
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

    function startGame() {
        startGameSocket.send("START-GAME");
    }

    function onMessageStartGameSocket(e) {
        const msg = e.data;
        if (msg === "START-GAME") {
            window.location.replace("http://" + location.host + "/" + artifact + "/GameplayServlet?roomID=" + rooom.ID);
        }
    }
</script>
