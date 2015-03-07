<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Админка</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Здравствуйте, ${client.firstName} ${client.lastName}</h1>
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
                <form method="post" action="client">
                    <input type="hidden" name="source" value="personal">
                    <input class="myButton" type="submit" value="Личные данные">
                </form>
            </li>
            <li><input class="myButton" type="button" value="Контракт"></li>
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
                <td><b>Номер: </b></td>
                <td>${contract.number}</td>
            </tr>
            <tr>
                <td><b>Тариф: </b></td>
                <td>${tariff.name}</td>
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
