<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java Mobile</title>
    <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
    <%--custom styles--%>
    <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src=="<c:url value="/resources/js/link_submit.js" />"></script>
</head>
<body>

<nav role="navigation" class="navbar navbar-inverse">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a href="#" class="navbar-brand" id="brand">Java Mobile</a>
    </div>
    <!-- Collection of nav links and other content for toggling -->
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="//j_spring_security_logout" />">Выйти</a></li>
    </ul>
</nav>

<div class="container">

    <table class="table table-striped table-bordered table-condensed">
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
    <table class="table table-striped table-bordered table-condensed">
        <tr>
            <th>Опция</th>
            <th>Цена</th>
            <th>Стоимость подключения</th>
            <th>Отключение</th>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>Недоступно</td>
            </tr>
        </c:forEach>
    </table>
    <form method="get" action="<c:url value='/user/unlockContractByClient' />">
        <button id="unlock_button" class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
            Разблокировать номер
        </button>
    </form>
</div>

</body>
</html>
