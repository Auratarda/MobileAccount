<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Админка</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Администратор ${sessionScope.firstName}</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="login.jsp">Логин</a>
        <br>
        <a href="client.jsp">Профиль</a>
    </div>
</div>

<div id="section">

    <div id="topNav">
        <ul>
            <li>
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="contracts">
                    <input class="myButton" type="submit" value="Контракты">
                </form>
            </li>
            <li><input class="myButton" type="button" value="Тарифы"></li>
            <li>
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="options">
                    <input class="myButton" type="submit" value="Опции">
                </form>
            </li>
        </ul>
    </div>

    <div class="table" id="tariff">
        <table class="innerTable">
            <tr>
                <th>Тариф</th>
                <th>Цена</th>
            </tr>
            <c:forEach var="tariff" items="${sessionScope.tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>