<%@ page import="javax.naming.Name" %>
<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.model.*" %><%--
  Created by IntelliJ IDEA.
  User: mariam
  Date: 04.09.22
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="JSP/forGameplay.css">
<%--<script type="application/javascript" src="gameroom.js"></script>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>--%>
<head>
    <title>Codenames</title>
</head>
<body>
<%
    List<String> words = (List<String>) request.getAttribute(NameConstants.WORDS);
    List<WordColor> colors = (List<WordColor>) request.getAttribute("colors");
    boolean isSpy = (boolean) request.getAttribute("isSpy");
    WordColor color0 = colors.get(0);
    Room room = (Room)request.getAttribute("myRoom");
%>


<html>
<script>

    function onMessageColorSocket (event) {
        console.log(event.data);
        let gameEvent = JSON.parse(event.data);
        let x = document.getElementById("mytable").getElementsByTagName("td");
        if(gameEvent.openedIndex != -1) {
            x[gameEvent.openedIndex].style.backgroundColor = gameEvent.colorOfIndex;
        }
        let turnColor = gameEvent.sideNow;
        if(turnColor == "RED"){
            document.getElementById("turn").innerHTML = "It is Red Team's Turn";
        } else {
            document.getElementById("turn").innerHTML = "It is Blue Team's Turn";
        }
        let rred = gameEvent.remainingRed.toString();
        let rblue = gameEvent.remainingBlue.toString();
        document.getElementById("red").innerHTML = "Red Words Left: " + rred;
        document.getElementById("blue").innerHTML = "Blue Words Left: " + rblue;
        if(${isSpy}){
            x[0].style.backgroundColor = "${colors.get(0)}";
            x[1].style.backgroundColor = "${colors.get(1)}";
            x[2].style.backgroundColor = "${colors.get(2)}";
            x[3].style.backgroundColor = "${colors.get(3)}";
            x[4].style.backgroundColor = "${colors.get(4)}";
            x[5].style.backgroundColor = "${colors.get(5)}";
            x[6].style.backgroundColor = "${colors.get(6)}";
            x[7].style.backgroundColor = "${colors.get(7)}";
            x[8].style.backgroundColor = "${colors.get(8)}";
            x[9].style.backgroundColor = "${colors.get(9)}";

            x[10].style.backgroundColor = "${colors.get(10)}";
            x[11].style.backgroundColor = "${colors.get(11)}";
            x[12].style.backgroundColor = "${colors.get(12)}";
            x[13].style.backgroundColor = "${colors.get(13)}";
            x[14].style.backgroundColor = "${colors.get(14)}";
            x[15].style.backgroundColor = "${colors.get(15)}";
            x[16].style.backgroundColor = "${colors.get(16)}";
            x[17].style.backgroundColor = "${colors.get(17)}";
            x[18].style.backgroundColor = "${colors.get(18)}";
            x[19].style.backgroundColor = "${colors.get(19)}";

            x[20].style.backgroundColor = "${colors.get(20)}";
            x[21].style.backgroundColor = "${colors.get(21)}";
            x[22].style.backgroundColor = "${colors.get(22)}";
            x[23].style.backgroundColor = "${colors.get(23)}";
            x[24].style.backgroundColor = "${colors.get(24)}";
        }
        let won = gameEvent.winner;
        if(won != null){
            alert(won + " TEAM WON!");
            window.location.replace("http://localhost:8080/Codenames_war_exploded/JSP/userPage.jsp");
        }
    }


    function onMessageChatSocket (event) {
        let chatMessage = JSON.parse(event.data);
        let name = chatMessage.username;
        let message = chatMessage.message;
        let prev = document.getElementById("chatarea").value;
        let newValue = prev + name + ": " + message + "\n" ;
        document.getElementById("chatarea").value = newValue;
        console.log(name);
    }

    let colorSocket = null;
    let chatSocket = null;
    window.onload = () => {
        colorSocket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/gameplay_ws?${NameConstants.ROOM_ID}=${roomID}");
        colorSocket.onmessage = onMessageColorSocket;

        chatSocket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/chat_ws?${NameConstants.ROOM_ID}=${roomID}");
        chatSocket.onmessage = onMessageChatSocket;
    }

    function changeBack(index){
        colorSocket.send(index);
    }

    function sendMessage(){
        let userMessage = document.getElementById("usermessage").value;
        chatSocket.send(userMessage);
    }
</script>


<b id="turn">It is Blue Teams' Turn</b>


<input onclick="changeBack(-1);" name="endturn" type="button" id="endturn" value="End Turn" /><br>
<b id="red">Red Words Left: </b> <br>
<b id="blue"> Blue Words Left: </b> <br>


<b id="redSpymasters">Red Spymasters:
    <%
        for(int i = 0; i < room.getRedSpymasters().size(); i++){
            Player curPlayer = room.getRedSpymasters().get(i);
            String curUsername = curPlayer.getUser().getUsername();
            out.print(curUsername + " ");
        }

    %>
</b> <br>
<b id="redOperatives">Red Operatives:
    <%
        for(int i = 0; i < room.getRedOperatives().size(); i++){
            Player curPlayer = room.getRedOperatives().get(i);
            String curUsername = curPlayer.getUser().getUsername();
            out.print(curUsername + " ");
        }
    %>


</b> <br>
<b id="blueSpymasters">Blue Spymasters:
    <%
        for(int i = 0; i < room.getBlueSpymasters().size(); i++){
            Player curPlayer = room.getBlueSpymasters().get(i);
            String curUsername = curPlayer.getUser().getUsername();
            out.print(curUsername + " ");
        }

    %>
</b> <br>
<b id="blueOperatives">Blue Operatives:
    <%
        for(int i = 0; i < room.getBlueOperatives().size(); i++){
            Player curPlayer = room.getBlueOperatives().get(i);
            String curUsername = curPlayer.getUser().getUsername();
            out.print(curUsername + " ");
        }

    %>
</b> <br>
</html>
<table id="mytable">
    <tr>
        <td onclick="changeBack(0);"> <% out.print(words.get(0)); %></td>
        <td onclick="changeBack(1);"> <% out.print(words.get(1)); %></td>
        <td onclick="changeBack(2);"> <% out.print(words.get(2)); %></td>
        <td onclick="changeBack(3);"> <% out.print(words.get(3)); %></td>
        <td onclick="changeBack(4);"> <% out.print(words.get(4)); %></td>
    </tr>
    <tr>
        <td onclick="changeBack(5);"> <% out.print(words.get(5)); %></td>
        <td onclick="changeBack(6);"> <% out.print(words.get(6)); %></td>
        <td onclick="changeBack(7);"> <% out.print(words.get(7)); %></td>
        <td onclick="changeBack(8);"> <% out.print(words.get(8)); %></td>
        <td onclick="changeBack(9);"> <% out.print(words.get(9)); %></td>
    </tr>
    <tr>
        <td onclick="changeBack(10);"> <% out.print(words.get(10)); %></td>
        <td onclick="changeBack(11);"> <% out.print(words.get(11)); %></td>
        <td onclick="changeBack(12);"> <% out.print(words.get(12)); %></td>
        <td onclick="changeBack(13);"> <% out.print(words.get(13)); %></td>
        <td onclick="changeBack(14);"> <% out.print(words.get(14)); %></td>
    </tr>
    <tr>
        <td onclick="changeBack(15);"> <% out.print(words.get(15)); %></td>
        <td onclick="changeBack(16);"> <% out.print(words.get(16)); %></td>
        <td onclick="changeBack(17);"> <% out.print(words.get(17)); %></td>
        <td onclick="changeBack(18);"> <% out.print(words.get(18)); %></td>
        <td onclick="changeBack(19);"> <% out.print(words.get(19)); %></td>
    </tr>
    <tr>
        <td onclick="changeBack(20);"> <% out.print(words.get(20)); %></td>
        <td onclick="changeBack(21);"> <% out.print(words.get(21)); %></td>
        <td onclick="changeBack(22);"> <% out.print(words.get(22)); %></td>
        <td onclick="changeBack(23);"> <% out.print(words.get(23)); %></td>
        <td onclick="changeBack(24);"> <% out.print(words.get(24)); %></td>
    </tr>
</table>



<br>

<textarea id="chatarea" rows="20" cols="30">
    </textarea>
<br>


<div id="wrapper">
    <%--    <div id="chatbox"></div>--%>

    <form name="message" action="">
        <input name="usermessage" type="text" id="usermessage" />
        <input onclick="sendMessage();" name="submitmessage" type="button" id="submitmessage" value="Send" />
    </form>
</div>


</body>
</html>
