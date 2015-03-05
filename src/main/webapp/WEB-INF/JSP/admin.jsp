<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Админка</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script>
        $(function () {
            $("#showContracts").click(function () {
                $('#tariff').hide();
                $('#contract').show();
            });
        });
    </script>
    <script>
        $(function () {
            $("#showTariffs").click(function () {
                $('#contract').hide();
                $('#tariff').show();
            });
        });
    </script>
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
            <li><a href="#" id="showContracts">Контракты</a></li>
            <li><a href="#" id="showTariffs">Тарифы</a></li>
            <li><a href="options.jsp">Опции</a></li>
            <li><a href="edit.jsp">Редактировать</a></li>
        </ul>
    </div>

    <div class="table" id="contract">
        <table>
            <tr>
                <th>Номер</th>
                <th>Клиент</th>
            </tr>
            <c:forEach var="contract" items="${sessionScope.contracts}">
                <tr>
                    <td>+7${contract.number}</td>
                    <td>${contract.client}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="table" id="tariff">
        <table>
            <tr>
                <th>Тариф</th>
                <th>Стоимость</th>
            </tr>
            <c:forEach var="tariff" items="${sessionScope.tariffs}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

<div id="footer">
    <p>
        CreatedBy © Stanchin Denis & Vasilevskii Stanislav
    </p>
</div>
</body>
</html>