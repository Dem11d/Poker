<%-- 
    Document   : requestManager
    Created on : 29.11.2016, 9:42:51
    Author     : Павлюки
--%>

<%@page import="controller.Constants"%>
<%@page import="java.util.Set"%>
<%@page import="controller.Bank"%>
<%@page import="controller.GameController"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.List"%>
<%@page import="controller.Player"%>
<%@page import="controller.GameServer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%=((GameServer)application.getAttribute("GameSer")).gameStatus()%>
        <br>
        <p id="step"><%=((GameServer)application.getAttribute("GameSer")).getStep()%></p>
        <br>
        <%
            GameServer gameServer = (GameServer)application.getAttribute("GameSer");
            if (!gameServer.gameStatus().equals("WAITING_FOR_PLAYERS")){
            out.append(gameServer.getGameController().getGameBoard().getBiggestRate()+"<br>");
            }
            
            %>
            <div class="board">
            <%
                //вывод кард стола
            if (!gameServer.gameStatus().equals("WAITING_FOR_PLAYERS")){
                List boardCards = gameServer.getGameController().getGameBoard().getBoardCards();
                if(Objects.isNull(boardCards)){
            }
                 else{
                out.append("<div class='board-cards'>"+view.PokerVisualUtils.getClassCards(boardCards)+"</div>");
                out.append("<br>");
            }
                
            
 //вывод банка
            out.append("<div class='banks'>");
            double mainBank = gameServer.getGameController().getGameBoard().getBank().getBalance();
            out.append("<span class='bank'>Банк: "+mainBank+"</span>");
            List<Bank> allinBanks = gameServer.getGameController().getGameBoard().getAllInBanks();
            if(allinBanks!=null&&allinBanks.size()>0){
                for(Bank bank:allinBanks){
                    out.append("<span class='bank'>Банк: "+bank.getBalance()+"</span>");
                }
            }
            }
            //вывод всех игроков
            Set winnerSet = gameServer.getWinnerSet();
            for(Player player:gameServer.getPlayers()){
              boolean activeRater = player.isRateAccess();
              //формирование класов-стилей игрока
              StringBuilder clazz = new StringBuilder();
              clazz.append("spot-"+gameServer.getPlayers().indexOf(player)+" ");
              clazz.append("player ");
              if(activeRater)
                  clazz.append("activeRater");
              out.append("<div class='"+clazz.toString()+"'>"); 
              
              out.append("Name: <span class='userName'>"+player.getName()+"</span><br>");
              out.append("Balance: "+player.getBalance()+"<br>");
              out.append("Rate "+player.getRate()+"<br>");
//              out.append("AccesRate "+player.isRateAccess()+"<br>");
              out.append("isAllin "+player.isAllIn()+"<br>");
              out.append("isFold "+player.isFold()+"<br>");
              if(player.isDealer()){
                   out.append("<div class='dealer'><span>D<span></div>");
              }
//              out.append("isDealer "+player.isIsDealer()+"<br>");
              if(player.isSmallBlind()){
                   out.append("<div class='blind'><span>sb<span></div>");
              }
              if(player.isBigBlind()){
                   out.append("<div class='blind'><span>BB<span></div>");
              }
//              out.append("isBB "+player.isBigBlind()+"<br>");
//              out.append("isSB "+player.isSmallBlind()+"<br>");
              if(session.getAttribute("UPlayer").equals(player)||winnerSet.contains(player)){
                  if(!(player.getCards()==null))
                  out.append(view.PokerVisualUtils.getClassCards(player.getCards()));
              }else{
                  // на случай если нужно смотреть все карты
                  if(Constants.SHOW_CARDS)
                  out.append(view.PokerVisualUtils.getClassCards(player.getCards()));
                  else 
                      out.append(view.PokerVisualUtils.getClosedCards(player.getCards()));
              }
              
              out.append("</div>");
//            out.append(player.getName()+" has balance: "+player.getBalance()+"<br>");
            
        }
        %>
        </div>
    </body>
</html>
