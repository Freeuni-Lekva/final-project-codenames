<%@ page import="com.example.codenames.service.PlayerHistoryService" %>
<%@ page import="com.example.codenames.listener.NameConstants" %>
<%@ page import="com.example.codenames.model.Game" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.codenames.service.implementation.PlayerHistoryServiceImpl" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.codenames.DTO.UserGamesDto" %>
<%@ page import="com.example.codenames.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        ServletContext sc = request.getServletContext();
        User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
    %>
    <title><%=user.getUsername()%></title>
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
        #game_history {
            border: 5px dashed orangered;
            right: 5px;
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
        .button_joinRandomRoom{
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

        .button_joinRandomRoom:hover {background-color: #3e8e41}

        .button_joinRandomRoom:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }

        .button_logout{
            display: inline-block;
            padding: 15px 25px;
            margin: 3px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            outline: none;
            text-decoration: none;
            color: chocolate;
            background-color: black;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .button_logout:hover {background-color: #3e8e41}

        .button_logout:active {
            background-color: black;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }



    </style>

</head>



<body>
    <div id="head_part">
    <div id="upper_part">
        <div id="headLine">Welcome <%=user.getUsername()%> </div>
    </div>
        <form style="vertical-align: center" action="../LogoutServlet" method="post">
            <button class="button_logout" >Log Out</button>
        </form>
    <div id="game_history">
        <div class="title_text">
            Your Games
            <div align="center">
            <table border="1" cellpadding="5"  WIDTH=700 >
                <tr>
                    <th>Game_id</th>
                    <th>Winner</th>
                    <th>My team</th>
                    <th>Points gained</th>
                    <th>Date</th>
                </tr>
                <%
                    PlayerHistoryServiceImpl service = (PlayerHistoryServiceImpl) sc.getAttribute(NameConstants.PLAYER_HISTORY_SERVICE);
                    List<UserGamesDto> games = service.getSortedGames(user.getUserID());
                    int i = 1;
                    for(UserGamesDto game : games) {
                        String pattern = "MM-dd-yyyy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        int points = 0;
                        if(game.getGames().getWinner().equals(game.getUserTeam())){
                            points = 1;
                        }
                        else{
                            if(game.getGames().isBlackWordSelected()) {
                                points = -1;
                            }
                        }
                        %>
                <tr>
                    <td><%=game.getGames().getGameID()%></td>
                    <td><%=game.getGames().getWinner()%></td>
                    <td><%=game.getUserTeam()%></td>
                    <td><%=points%></td>
                    <td><%=simpleDateFormat.format(game.getGames().getDate())%></td>
                </tr>
                <% i++;
                } %>
            </table>
        </div>
        </div>
    </div>

    <div id="personal_info">
        <div class="personal_texts">
            <h3>Personal information</h3>
            Name: <%=user.getUsername()%><br>
            Points: <%=user.getPoints()%><br>
            Games played: <%=user.getGamesPlayed()%><br>
            Games won: <%=user.getGamesWon()%><br>
            Games lost: <%=user.getGamesLost()%><br>
            Black words selected: <%=user.getBlackWordCounter()%><br>
            Winning rate: <%=user.getWinningRate()%><br>
            Registration date : <%=user.getRegistrationDate()%><br>
            <div id = "leaderboard_border" >
                <div align="center">
                    <h4>See leaderboard</h4>
                    <a href="leaderboard.jsp" class="button_leaderboard">Leaderboard</a>
                </div>
            </div>
            <div id = "create_border" >
                <div align="center">
                    <h4>Create new room</h4>
                    <form action="chooseCategories.jsp" method="post">
                        <button class="button_createRoom">Create room</button><br>
                    </form>

                </div>
            </div>
            <div id = "join_border" >
                <div align="center">
                    <h4>Join the room</h4>
                    <form action="../JoinRoomServlet" method="get">
                        <button class="button_joinRoom">Join</button><br>
                    </form>
                </div>
            </div>

            <div id = "join_Random_border" >
                <div align="center">
                    <h4>Join random room</h4>
                    <form action="../JoinRandomRoomServlet" method="post">
                        <button class="button_joinRandomRoom">Join</button><br>
                    </form>

                </div>
            </div>


        </div>

    </div>




</body>
</html>
