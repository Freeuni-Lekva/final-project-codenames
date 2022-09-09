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
        #user_list{
            border: 5px dashed orangered;
            position: absolute;
            margin: 1%;
            top: 10%;
            width: 48.5%;
            border-radius: 10px;
        }
        #personal_info {
            border: 5px black;
            left:5px;
            position: absolute;
            margin: 1%;
            top: 10%;
            width: 48.5%;
            border-radius: 15px;
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
        .title_text {
            text-align: center;
            font-size: 1.8vw;
            margin-bottom: 1.5vw;
            margin-top: 1.5vw;
        }
        .personal_texts{
            text-align: left;
            font-size: 1.4vw;
            color: black;
        }
        #leaderboard_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 1px;
            border-radius: 10px;
            height: 200px;
            width: 250px;
        }
        #create_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 265px;
            border-radius: 10px;
            height: 200px;
            width: 250px;
        }
        #join_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 1px;
            top: 600px;
            border-radius: 10px;
            height: 200px;
            width: 250px;
        }
        #join_Random_border {
            border: 2px dashed orangered;
            position: absolute;
            margin: 1%;
            left: 265px;
            top: 600px;
            border-radius: 10px;
            height: 200px;
            width: 250px;
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
        .button_createRoom {
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

        .button_createRoom:hover {background-color: #3e8e41}

        .button_createRoom:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .button_joinRoom{
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

        .button_joinRoom:hover {background-color: #3e8e41}

        .button_joinRoom:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .delete{
            display: inline-block;
            padding: 3px 5px;
            margin: 0.5px;
            font-size: 3px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: ivory;
            background-color: orangered;
            border: none;
            border-radius: 3px;
            box-shadow: 0 3px #999;
        }

        .delete:hover {background-color: #3e8e41}

        .delete:active {
            background-color: orangered;
            box-shadow: 0 2px #666;
            transform: translateY(1px);
        }
    </style>

    <div id="head_part">
        <div id="headLine">Welcome Admin</div>
    </div>

    <div id="user_list">

        <div align="center">
            <table border="1" cellpadding="5"  WIDTH=700>
                <tr>
                    <th>Rating</th>
                    <th>Username</th>
                    <th>Points</th>
                    <th>Delete</th>
                </tr>
                <%
                    ServletContext sc = request.getServletContext();
                    UserServiceImpl service = (UserServiceImpl) sc.getAttribute(NameConstants.USER_SERVICE);
                    List<User> users = service.getUsersByPoints(true);
                    int i = 1;
                    for(User user : users) {%>
                <tr>
                    <td><%=i%></td>
                    <td><%=user.getUsername()%></td>
                    <td><%=user.getPoints()%></td>
                    <td>
                        <form action="DeleteUserServlet" method="post">
                            <input type="image" name="Name of image button" src="https://as1.ftcdn.net/v2/jpg/03/46/38/40/1000_F_346384068_e06I3cC4n0BCyB8f5PZ9cG2YR3N68ZYc.jpg" style="width: 40px; height: 40px"  alt="delete">
                            <input type="hidden" id=<%=NameConstants.USER_ID%> name=<%=NameConstants.USER_ID%> value=<%=user.getUserID()%>>
                        </form>

                    </td>
                </tr>
                <% i++;
                } %>

            </table>
        </div>





    </div>


</head>
<body>


</body>
</html>
