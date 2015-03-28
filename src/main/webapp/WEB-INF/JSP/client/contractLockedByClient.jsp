<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
</head>
<body>

<div id="header">
    <h1>Личный кабинет (${client.firstName} ${client.lastName})</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="<c:url value="/index.jsp" />">Выйти</a>
    </div>
</div>

<div id="section">

    <div id="topNav"></div>

    <div id="blocked">Номер заблокирован!</div>

    <div class="table" id="contract">
        <table class="innerTable">
            <tr>
                <td><b>Номер: </b></td>
                <td>+7${contract.number}</td>
            </tr>
            <tr>
                <td><b>Тариф: </b></td>
                <td>${tariff.name}</td>
            </tr>
            <tr>
                <td><b>Цена: </b></td>
                <td>${tariff.price}</td>
            </tr>
        </table>
        <br>
        <table class="innerTable">
            <tr>
                <th>Опция</th>
                <th>Цена</th>
                <th>Стоимость подключения</th>
                <th>Отключение</th>
            </tr>
            <c:forEach var="option" items="${options}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td>Недоступно</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <form method="post" action="<c:url value='/main/unlockContractByClient' />">
        <input type="hidden" name="contract" value="${contract.number}">
        <input class="link" type="submit" id="innerButton" value="Разблокировать номер">
    </form>
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>
