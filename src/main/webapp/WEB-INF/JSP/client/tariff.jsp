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
    <ul class="nav navbar-nav">
        <li class="navbar-item"><a href="<c:url value='/user/showPersonal' />">Личные данные</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showContract' />">Контракт</a></li>
        <li class="navbar-item" id="current-menu-item"><a href="<c:url value='/user/showTariffs' />">Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showOptions' />">Опции</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showBasket' />" >Корзина</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
    </ul>
</nav>

<div class="container">
        <table class="table table-striped table-bordered table-condensed">
        <tr>
            <th>Тариф</th>
            <th>Цена</th>
            <th>Смена тарифа</th>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <form method="post" action="<c:url value='/user/changeTariff' />">
                        <input type="hidden" name="tariffName" value="${tariff.name}">
                        <button class="btn btn-sm btn-success" type="submit" name="submit">
                            Перейти
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </table>
    </div>

</body>
</html>
