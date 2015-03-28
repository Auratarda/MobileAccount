<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Кабинет администратора</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
</head>
<body>

<div id="header">
    <h1>Администратор</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="<c:url value="/index.jsp" />">Выйти</a>
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
            <li>
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="tariffs">
                    <input class="myButton" type="submit" value="Тарифы">
                </form>
            </li>
            <li><input class="myButton" type="button" value="Опции"></li>
        </ul>
    </div>

    <div class="row">
        <div class="col">
    <div class="table" id="option">
        <table class="innerTable">
            <tr>
                <th>Опция</th>
                <th>Цена</th>
                <th>Подключение</th>
                <th>Удалить</th>
            </tr>
            <c:forEach var="option" items="${allOptions}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td>
                        <form method="post" action="admin">
                            <input type="hidden" name="source" value="removeOption">
                            <input type="hidden" name="optionName" value="${option.name}">
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
                <input type="hidden" name="source" value="addNewOption">

                <div id="tariff">
                    <table id="clientForm">
                        <tr>
                            <td>Название опции:</td>
                            <td><input type="text" name="optionName" maxlength="30" required></td>
                        </tr>
                        <tr>
                            <td>Цена:</td>
                            <td><input type="number" name="optionPrice" maxlength="10" required></td>
                        </tr>
                        <tr>
                            <td>Стоимость подключения:</td>
                            <td><input type="number" name="connectionCost" maxlength="10" required></td>
                        </tr>
                    </table>
                </div>
                <input class="myButton" id="assignNewContractButton" type="submit" value="Добавить новую опцию">
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