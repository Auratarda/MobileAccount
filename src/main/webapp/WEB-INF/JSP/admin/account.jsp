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
                        <td><b>Имя: </b></td>
                        <td>${client.firstName}</td>
                    </tr>
                    <tr>
                        <td><b>Фамилия: </b></td>
                        <td>${client.lastName}</td>
                    </tr>
                    <tr>
                        <td><b>Дата рождения: </b></td>
                        <td>${dateOfBirth}</td>
                    </tr>
                    <tr>
                        <td><b>Емейл: </b></td>
                        <td>${client.email}</td>
                    </tr>
                    <tr>
                        <td><b>Адрес: </b></td>
                        <td>${client.address}</td>
                    </tr>
                    <tr>
                        <td><b>Паспорт: </b></td>
                        <td>${client.passport}</td>
                    </tr>
                </table>
            </div>
            <div class="blockInline">
                <form method="post" action="admin">
                    <input type="hidden" name="source" value="removeClient">
                    <input type="hidden" name="contractToRemove" value="${contract.number}">
                    <input class="myButton" id="assignNewContractButton" type="submit" value="Удалить клиента">
                </form>
            </div>
            <div class="blockInline">
                <c:if test="${sessionScope.status!='Заблокирован оператором'}">
                    <div class="operatorBlockButton">
                        <form method="post" action="admin">
                            <input type="hidden" name="source"
                                   value="lockContract">
                            <input type="hidden" name="contractNumber" value="${contract.number}">
                            <input class="myButton" type="submit" value="Заблокировать номер">
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionScope.status=='Заблокирован оператором'}">
                    <div class="operatorBlockButton">
                        <form method="post" action="admin">
                            <input type="hidden" name="source" value="unLockContract">
                            <input type="hidden" name="contractNumber" value="${contract.number}">
                            <input class="myButton" type="submit" value="Разблокировать номер">
                        </form>
                    </div>
                </c:if>
            </div>

            <div class="table" id="clearFix">
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
                    <tr>
                        <td><b>Состояние: </b></td>
                        <td>${status}</td>
                    </tr>
                </table>

                <div class="search">
                    <form method="post" action="admin">
                        <input type="hidden" name="source" value="changeTariff">
                        <input type="hidden" name="contractNumber" value="${contract.number}">
                        <select name="tariffs[]" required>
                            <c:forEach var="tariff" items="${tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </select>
                        <input class="myButton" type="submit" value="Поменять тариф">
                    </form>
                </div>
            </div>
        </div>

        <div class="col">
            <div id="rightCol">
                <table class="innerTable">
                    <tr>
                        <th>Опция</th>
                        <th>Цена</th>
                        <th>Подключение</th>
                        <th>Отключение</th>
                    </tr>
                    <c:forEach var="option" items="${options}">
                        <tr>
                            <td>${option.name}</td>
                            <td>${option.price}</td>
                            <td>${option.connectionCost}</td>
                            <td>
                                <form method="post" action="admin">
                                    <input type="hidden" name="source" value="removeOptionFromContract">
                                    <input type="hidden" name="optionName" value="${option.name}">
                                    <input type="hidden" name="currentContract" value="${contract.number}">
                                    <input class="link" type="submit" value="Отключить">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="search">
                    <form method="post" action="admin">
                        <input type="hidden" name="source" value="addMoreOptions">
                        <input type="hidden" name="contractNumber" value="${contract.number}">
                        <select multiple size="5" name="options[]">
                            <c:forEach var="option" items="${allOptions}">
                                <option value="${option.name}">${option.name}</option>
                            </c:forEach>
                        </select>

                        <div><input class="myButton" type="submit" value="Добавить опции"></div>
                    </form>
                </div>
            </div>
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
