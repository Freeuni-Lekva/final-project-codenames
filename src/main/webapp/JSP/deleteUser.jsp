<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.service.implementation.UserServiceImpl" %><%--
  Created by IntelliJ IDEA.
  User: ruska-ubuntu
  Date: 10.09.22
  Time: 02:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete</title>
    <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }
        #head{
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
        #user_list{
            border: 5px dashed orangered;
            position: absolute;
            margin: 1%;
            top: 10%;
            left: 150px;
            width: 80%;
            border-radius: 10px;
        }
    </style>

</head>
<body>
    <div id="head">
        <div id="headLine">Delete User</div>
    </div>
    <div align="center">
        <div id="user_list">

            <div align="center">
                <table border="1" cellpadding="5"  WIDTH=850>
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
                            <form action="../DeleteUserServlet" method="post">
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
    </div>

</body>
</html>
