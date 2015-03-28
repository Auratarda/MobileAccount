<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
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

    <div id="loginError">Сожалеем, Ваш <b>емаил</b> или <b>пароль</b> введены неверно.<br>
        Попробуйте ввести данные еще раз!<br>
        <a href="<c:url value="/index.jsp" />"> <input type="button" class="myButton" value="Логин"/></a>
    </div>
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>


