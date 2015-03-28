<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Кабинет администратора</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
    <script src=="<c:url value="/resources/js/link_submit.js" />"></script>
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
            <li><input class="myButton" type="button" value="Контракты"></li>
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
            >
        </ul>
    </div>

    <div class="row">
        <div class="col">
            <div class="table" id="contract">
                <table class="innerTable">
                    <tr>
                        <th>Клиент</th>
                        <th>Номер</th>
                    </tr>
                    <c:forEach var="contract" items="${allContracts}">
                        <tr>
                            <td>${contract.client}</td>
                            <td>
                                <form action="<c:url value='/main/showClient' />" method="post">
                                    <a href="javascript:;"
                                       onclick="get_form(this).submit(); return false">+7${contract.number}</a>
                                    <input type="hidden" name="number" value=${contract.number}>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div class="col">
            <div>
                <form method="post" action="<c:url value='/main/assignNewContract' />">
                    <input class="myButton" id="assignNewContractButton" type="submit" value="Заключить новый контракт">
                </form>
            </div>
            <div class="search">
                <form method="post" action="<c:url value='/main/findClientByNumber' />">
                    +7<input type="text" name="searchNumber" placeholder="Введите номер телефона" maxlength="10"
                             required>
                    <input class="myButton" type="submit" value="Искать">
                </form>
            </div>
            <c:if test="${sessionScope.notFound=='notFound'}">
                <div class="search">Абонент не зарегистрирован в системе</div>
            </c:if>
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