<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Кабинет администратора</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <script src="js/link_submit.js"></script>
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
            <li><input class="myButton" type="button" value="Контракты"></li>
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

    <div>
        <form method="post" action="admin">
            <input type="hidden" name="source" value="assignNewContract">
            <input class="myButton" id="assignNewContractButton"
                   type="submit" value="Заключить новый контракт">
        </form>
    </div>


    <div class="table" id="contract">
        <table class="innerTable">
            <tr>
                <th>Клиент</th>
                <th>Номер</th>
            </tr>
            <c:forEach var="contract" items="${sessionScope.contracts}">
                <tr>
                    <td>${contract.client}</td>
                    <td>
                        <form action="" method="post">
                            <a href="javascript:;"
                               onclick="get_form(this).submit(); return false">+7${contract.number}</a>
                            <input type="hidden" name="source" value="client">
                            <input type="hidden" name="number" value=${contract.number}>
                        </form>
                    </td>
                </tr>
            </c:forEach>
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