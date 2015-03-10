<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Кабинет администратора</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Администратор</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="logout">Выйти</a>
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

    <div class="row">
        <div class="col">
            <div class="table">
                <table class="innerTable">
                    <tr>
                        <th>Тариф</th>
                        <th>Цена</th>
                        <th>Удалить</th>
                    </tr>
                    <c:forEach var="tariff" items="${sessionScope.tariffs}">
                        <tr>
                            <td>${tariff.name}</td>
                            <td>${tariff.price}</td>
                            <td>
                                <form method="post" action="admin">
                                    <input type="hidden" name="source" value="removeTariff">
                                    <input type="hidden" name="tariffName" value="${tariff.name}">
                                    <input class="link" type="submit" value="Удалить">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div class="col">
            <form method="post" action="admin">
                <input type="hidden" name="source" value="addNewTariff">

                <div id="tariff">
                    <table id="clientForm">
                        <tr>
                            <td>Название тарифа:</td>
                            <td><input type="text" name="tariffName" maxlength="30" required></td>
                        </tr>
                        <tr>
                            <td>Цена:</td>
                            <td><input type="number" name="tariffPrice" maxlength="10" required></td>
                        </tr>
                    </table>
                </div>
                <input class="myButton" id="assignNewContractButton" type="submit" value="Добавить новый тариф">
            </form>
        </div>
    </div>
</div>


<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>