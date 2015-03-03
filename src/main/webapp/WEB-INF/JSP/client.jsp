<%--
  Created by IntelliJ IDEA.
  User: Stanislav
  Date: 03.03.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="css/client.css"/>
</head>
<body>
<div align="center">
    <h3>Добро пожаловать в Ваш личный кабинет, ${sessionScope.firstName} ${sessionScope.lastName}!</h3>

    <table>
        <tr>
            <td><b>Имя: </b></td>
            <td>${sessionScope.firstName}</td>
        </tr>
        <tr>
            <td><b>Фамилия: </b></td>
            <td>${sessionScope.lastName}</td>
        </tr>
        <tr>
            <td><b>Дата рождения: </b></td>
            <td>${sessionScope.dateOfBirth}</td>
        </tr>
        <tr>
            <td><b>Емейл: </b></td>
            <td>${sessionScope.email}</td>
        </tr>
        <tr>
            <td><b>Адрес: </b></td>
            <td>${sessionScope.address}</td>
        </tr>
        <tr>
            <td><b>Паспорт: </b></td>
            <td>${sessionScope.passport}</td>
        </tr>
    </table>
    <a id="main" href="http://localhost:8080/index.jsp"> <input type="button" value="На Главную"/></a>
</div>
</body>
</html>
