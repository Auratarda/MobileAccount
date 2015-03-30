<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
    <script src="<c:url value="/resources/js/link_submit.js" />"></script>
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

    <div id="topNav">
        <ul>
            <li>
                <form method="post" action="<c:url value='/main/showPersonal' />">
                    <input type="hidden" name="contract" value="${contract.number}">
                    <input class="myButton" type="submit" value="Личные данные">
                </form>
            </li>
            <li><input class="myButton" type="button" value="Контракт"></li>
            <li>
                <form method="post" action="<c:url value='/main/showTariffs' />">
                    <input type="hidden" name="contract" value="${contract.number}">
                    <input class="myButton" type="submit" value="Тарифы">
                </form>
            </li>
            <li>
                <form method="post" action="<c:url value='/main/showOptions' />">
                    <input type="hidden" name="contract" value="${contract.number}">
                    <input class="myButton" type="submit" value="Опции">
                </form>
            </li>
        </ul>
    </div>

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
            <c:if test="${options.size()==0}">
                <tr><td colspan="4">
                <form action="<c:url value='/main/showOptions' />" method="post">
                    У Вас нет дополнительных опций.
                    <a href="javascript:;"
                       onclick="get_form(this).submit(); return false">Подключить</a>
                    <input type="hidden" name="contract" value="${contract.number}">
                </form>
                </td></tr>
            </c:if>
            <c:forEach var="option" items="${options}">
                <tr>
                    <td>${option.name}</td>
                    <td>${option.price}</td>
                    <td>${option.connectionCost}</td>
                    <td>
                        <form method="post" action="<c:url value='/main/removeOption' />">
                            <input type="hidden" name="contract" value="${contract.number}">
                            <input type="hidden" name="optionName" value="${option.name}">
                            <input class="link" type="submit" value="Отключить">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <form method="post" action="<c:url value='/main/lockContractByClient' />">
        <input type="hidden" name="contract" value="${contract.number}">
        <input class="link" id="innerButton" type="submit" value="Заблокировать номер">
    </form>
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>
