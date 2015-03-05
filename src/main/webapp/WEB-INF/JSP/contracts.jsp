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
            <li><input class="myButton" type="button" value="Контракты"></li>
            <li>
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="tariffs">
                    <input class="myButton" type="submit" value="Тарифы">
                </form>
            </li>
            <li>
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="options">
                    <input class="myButton" type="submit" value="Опции">
                </form>
            </li>
        </ul>
    </div>

    <div class="table" id="contract">
        <table>
            <tr>
                <th>Клиент</th>
                <th>Номер</th>
            </tr>
            <c:forEach var="contract" items="${sessionScope.contracts}">
                <tr>
                    <td>${contract.client}</td>
                    <td>+7${contract.number}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div id="footer">
    <p>
        CreatedBy © Stanchin Denis & Vasilevskii Stanislav
    </p>
</div>
</body>
</html>