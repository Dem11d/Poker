<%-- 
    Document   : setRate
    Created on : 29.11.2016, 18:34:59
    Author     : Demyd
--%>

<%@page import="controller.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            double rate =Double.parseDouble(request.getParameter("rate"));
            Player player = (Player)session.getAttribute("UPlayer");
            if(player.isRateAccess()){
            player.incrementRate(rate);
            while(player.isRateAccess())
                    player.setRateAccess(false);
            }
            %>
            
    </body>
</html>
