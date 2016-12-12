<%-- 
    Document   : index
    Created on : 25.11.2016, 13:53:46
    Author     : Павлюки
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Добро пожаловать в Покер</h1>
        <form action="main.jsp" method="POST">
        <p>Введите имя</p>
        <input type="text" name="userName">
        <button type="submit">Войти</button>
        </form>
    </body>
</html>
