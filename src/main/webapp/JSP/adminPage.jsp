<%@ page import="com.example.codenames.model.User" %>
<%@ page import="com.example.codenames.service.implementation.UserServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.listener.NameConstants" %><%--
  Created by IntelliJ IDEA.
  User: ruska-ubuntu
  Date: 08.09.22
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }
        #head_part {
            position: absolute;
            height: 10%;
            width: 100%;
            background-color: chocolate;
            margin: 0;
        }
        #headLine {
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            text-align: center;
            font-size: 3vw;
        }
        #delete_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 1px;
            top: 150px;
            border-radius: 10px;
            height: 200px;
            width: 350px;
        }
        #add_word_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 1px;
            top: 400px;
            border-radius: 10px;
            height: 200px;
            width: 350px;
        }
        #delete_word_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 400px;
            top: 150px;
            border-radius: 10px;
            height: 200px;
            width: 350px;
        }
        #leaderboard_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 400px;
            top: 400px;
            border-radius: 10px;
            height: 200px;
            width: 350px;
        }
        .button_leaderboard {
            display: inline-block;
            padding: 15px 25px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: ivory;
            background-color: orangered;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .button_leaderboard:hover {background-color: #3e8e41}

        .button_leaderboard:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .button_delete_word {
            display: inline-block;
            padding: 15px 25px;
            margin: 3px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: ivory;
            background-color: orangered;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .button_delete_word:hover {background-color: #3e8e41}

        .button_delete_word:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .add_word{
            display: inline-block;
            padding: 15px 25px;
            margin: 3px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: ivory;
            background-color: orangered;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .add_word:hover {background-color: #3e8e41}

        .add_word:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .delete{
            display: inline-block;
            padding: 15px 25px;
            margin: 3px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: ivory;
            background-color: orangered;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .delete:hover {background-color: #3e8e41}

        .delete:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
    </style>
</head>

<body>

    <div id="head_part">
        <div id="headLine">Welcome Admin !!</div>
    </div>

    <div id = "delete_border" >
        <div align="center">
            <h3>If you want to delete users click here</h3>
            <form action="DeleteServlet" method="post">
                <button class="delete">Delete users</button><br>
            </form>

        </div>
    </div>

    <div id = "add_word_border" >
        <div align="center">
            <h3>If you want to add words click here</h3>
            <form action="AddWordsServlet" method="post">
                <button class="add_word">Add Words</button><br>
            </form>

        </div>
    </div>

    <div id = "delete_word_border" >
        <div align="center">
            <h3>If you want to delete words click here</h3>
            <form action="DeleteWordsServlet" method="post">
                <button class="button_delete_word">Delete Words</button><br>
            </form>

        </div>
    </div>
    <div id = "leaderboard_border" >
        <div align="center">
             <h3>See leaderboard</h3>
            <a href="JSP/leaderboard.jsp" class="button_leaderboard">Leaderboard</a>
        </div>
    </div>

</body>
</html>
