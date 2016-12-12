<%@page import="controller.GameServer"%>
<%@page import="java.lang.String"%>
<%
  //  int StepFromUser =0;    
  int StepFromUser;
  try{  
  StepFromUser =Integer.parseInt((String)request.getParameter("step"));
  }
  catch (Exception e){
      StepFromUser=0;
  }
    GameServer gameServer = (GameServer)application.getAttribute("GameSer");
    int StepFromServer = gameServer.getStep();
    if(StepFromServer!=StepFromUser)
    out.append("true");
    else
    out.append("false");
    
%>