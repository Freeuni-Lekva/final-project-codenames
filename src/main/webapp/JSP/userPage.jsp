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
    <title>UserPage</title>
    <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }
        #upper_part {
            position: absolute;
            height: 10%;
            width: 100%;
            background-color: ivory;
            margin: 0;
        }
        #game_history {
            border: 5px black;
            right: 10px;
            position: absolute;
            margin: 1%;
            top: 10%;
            width: 48.5%;
            border-radius: 15px;
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

        #second_part {
            border: 2px dashed lightgreen;
            position: absolute;
            margin: 1%;
            top: 10%;
            left: 50%;
            width: 48.5%;
            border-radius: 10px;
        }
        img.background{
            position: absolute;
            left: 0px;
            top: 0px;
            z-index: -1;
            width: 100%;
            height: 100%;
            -webkit-filter: blur(5px); /* Safari 6.0 - 9.0 */
            filter: blur(10px);
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
        .button {
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

        .button:hover {background-color: #3e8e41}

        .button:active {
            background-color: orangered;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }



    </style>

</head>

<%
    ServletContext sc = request.getServletContext();
    User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
%>

<body>
<img class = "background" src="backgroundForIndex.jpg" >
    <div id="upper_part">
        <div id="headLine">Welcome <%=user.getUsername()%> </div>
    </div>
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
            <h4>See leaderboard</h4>

            <a href="JSP/leaderboard.jsp" class="button">Leaderboard</a>
        </div>

    </div>







</body>
</html>