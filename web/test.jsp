<%@page import="db.TryConnection"%>
<%
//    out.append(String.valueOf(TryConnection.check()));
if(TryConnection.check()){
    out.append("lalaal");
}
%>