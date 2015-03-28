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
                <form method="post" action="<c:url value='/main/showAllContracts' />">
                    <input class="myButton" type="submit" value="Контракты">
                </form>
            </li>
            <li>
                <form method="post" action="<c:url value='/main/showAllTariffs' />">
                    <input class="myButton" type="submit" value="Тарифы">
                </form>
            </li>
            <li>
                <form method="post" action="<c:url value='/main/showAllOptions' />">
                    <input class="myButton" type="submit" value="Опции">
                </form>
            </li>
        </ul>
    </div>

    <div class="table">
        <form method="post" action="<c:url value='/main/addNewClient' />">
            <table id="clientForm">
                <tr>
                    <td>Имя:</td>
                    <td><input type="text" name="firstName" maxlength="30" required></td>
                </tr>
                <tr>
                    <td>Фамилия:</td>
                    <td><input type="text" name="lastName" maxlength="30" required></td>
                </tr>
                <tr>
                    <td>Дата Рождения:</td>
                    <td><input type="date" name="dateOfBirth"></td>
                </tr>
                <tr>
                    <td>Паспортные данные:</td>
                    <td><input type="text" name="passport" maxlength="255"></td>
                </tr>
                <tr>
                    <td>Адрес:</td>
                    <td><input type="text" name="address" maxlength="255"></td>
                </tr>
                <tr>
                    <td>Емейл:</td>
                    <td><input type="email" name="email" maxlength="30" required></td>
                </tr>
                <tr>
                    <td>Пароль:</td>
                    <td><input type="password" name="password" maxlength="30" required></td>
                </tr>
                <tr>
                    <td>Выберите номер телефона</td>
                    <td>
                        <select name="numbers[]" required>
                            <c:forEach var="contract" items="${freeContracts}">
                                <option value="${contract.number}">+7${contract.number}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Выберите тариф</td>
                    <td>
                        <select name="tariffs[]" required>
                            <c:forEach var="tariff" items="${tariffs}">
                                <option value="${tariff.name}">${tariff.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Выберите опции</td>
                    <td>
                        <select multiple size="5" name="options[]">
                            <c:forEach var="option" items="${allOptions}">
                                <option value="${option.name}">${option.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="0"><input class="myButton" type="SUBMIT" value="Заключить договор с абонентом"></td>
                </tr>
            </table>
        </form>
    </div>

</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>