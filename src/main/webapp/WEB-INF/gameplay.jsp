<%@ page import="javax.naming.Name" %>
<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.model.Board" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.model.WordColor" %><%--
  Created by IntelliJ IDEA.
  User: mariam
  Date: 04.09.22
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="JSP/forGameplay.css">
<head>
    <title>Title</title>
</head>
<body>
<%  Board board = (Board) request.getAttribute(NameConstants.BOARD);
    List<String> words = board.getWords();
    List<WordColor> colors = board.getColors();

%>


<html>
<head>
    <script>
        function changeBack(index, color){
            var x = document.getElementById("mytable").getElementsByTagName("td");
            var colorss = JSON.parse('${colors}');
            x[index].style.backgroundColor = colorss[index];
        }
    </script>
</head>

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
</body>
</html>