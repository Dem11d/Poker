<%-- 
    Document   : main
    Created on : 25.11.2016, 13:56:35
    Author     : Павлюки
--%>

<%@page import="controller.Player"%>
<%@page import="java.util.Objects"%>
<%@page import="controller.TestPlayer"%>
<%@page import="controller.GameServer"%>
<link href="style/style.css" rel="stylesheet" type="text/css">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="js/tools.js" type="text/javascript"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
//        out.append(session.getId());
//        out.append("<br>");
        %>
        <% request.setCharacterEncoding("utf-8");%>
        <p> Здравствуйте,<span id="name">${param["userName"]}</span>!</p>
        <button id="join">Сесть за стол</button>
        <jsp:useBean id="GameSer" class="controller.GameServer" scope="application"></jsp:useBean>
        
        <jsp:useBean id="UPlayer" class="controller.Player" scope="session"></jsp:useBean>
        <%
            UPlayer.setName((String)request.getParameter("userName"));
//          out.append(((Player)session.getAttribute("UPlayer")).getName());
//          if(!Objects.isNull(GameSer.getPlayers())&&!GameSer.getPlayers().contains(UPlayer))
//          GameSer.addPlayer(UPlayer);
        %>
        <br>
        <p id="gameStatus"></p>
        <br>
        <%//GameSer.addPlayer(new TestPlayer());%>
        <!--<span>Количество игроков в игре</span><%=GameSer.countOfPlayers()%>-->
        <div class="rateWindow">
            <p>Введите ставку</p>
            <input id="rate" type="number" value="0">
            <button id="acceptRate"> Подтвердить ставку</button>
            <button id="check">Чек</button>
            <button id="fold">Сбросить</button>
            <br>
            <div id="panel">
                <button id="addBot"  >Добавить компьютерного игрока</button>
            <button id="restart" >Рестарт</button>
            <button id="bbUp" >Увеличить ББ</button>
            </div>
        </div>
    </body>
</html>
