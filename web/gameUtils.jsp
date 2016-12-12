<%@page import="controller.TestPlayer"%>
<%@page import="controller.GameServer"%>
<%
    String action =(String) request.getParameter("action");
    out.append("wow"+action);
    GameServer gameServer = (GameServer)application.getAttribute("GameSer");
    if(action.equals("addBot")){
            gameServer.addPlayer(new TestPlayer());
    }else if(action.equals("restart")){
            gameServer.newGame();
    }else if(action.equals("bbUp")){
            try{
                double bb = gameServer.getGameController().getGameBoard().getBigBlibd();
                gameServer.getGameController().getGameBoard().setBigBlibd(bb+15.00);
            }catch(NullPointerException ex){
            }
    }else if(action.equals("bbDown")){
             try{
                double bb = gameServer.getGameController().getGameBoard().getBigBlibd();
                gameServer.getGameController().getGameBoard().setBigBlibd(bb-15.00);
            }catch(NullPointerException ex){
            }   
            }
            
    %>