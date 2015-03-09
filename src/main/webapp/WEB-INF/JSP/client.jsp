<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Личный кабинет (${client.firstName} ${client.lastName})</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="logout">Выйти</a>
    </div>
</div>

<div id="section">

    <div id="topNav">
        <ul>
            <li><input class="myButton" type="button" value="Личные данные"></li>
            <li>
                <form method="post" action="client">
                    <input type="hidden" name="source" value="contract">
                    <input class="myButton" type="submit" value="Контракт">
                </form>
            </li>
            <li>
                <form method="post" action="client">
                    <input type="hidden" name="source" value="tariffs">
                    <input class="myButton" type="submit" value="Тарифы">
                </form>
            </li>
            <li>
                <form method="post" action="client">
                    <input type="hidden" name="source" value="options">
                    <input class="myButton" type="submit" value="Опции">
                </form>
            </li>
        </ul>
    </div>

    <div class="table" id="contract">
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
</div>

<div id="footer">
    <p>
        CreatedBy © Vasilevskii Stanislav
    </p>
</div>
</body>
</html>
