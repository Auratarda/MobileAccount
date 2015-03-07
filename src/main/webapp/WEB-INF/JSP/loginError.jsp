<%--
  Created by IntelliJ IDEA.
  User: Stanislav
  Date: 03.03.2015
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка авторизации</title>
</head>
<body>
<h3>Сожалеем, Ваш <b>емаил</b> или <b>пароль</b> введены неверно.<br>
    Попробуйте ввести данные еще раз!</h3>
<a href="index.jsp"> <input type="button" value="На Главную"/></a>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Мобильный оператор Java School</h1>
</div>
<div id="nav">
    <div class="link">
    </div>
</div>

<div id="section">

    <div id="topNav"></div>

    <div id="loginError">Сожалеем, Ваш емаил или пароль введены неверно.<br>
        Попробуйте ввести данные еще раз!<br>
        <a href="index.jsp"> <input type="button" class="myButton" value="Логин"/></a>
    </div>
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>


