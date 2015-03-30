<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/JSP/header.jsp" %>
<html>
<head>
    <title>Java Mobile</title>
    <link rel="stylesheet" href="<c:url value="/myresources/css/style.css" />" type="text/css">
    <script type="text/javascript" src=="<c:url value="/myresources/js/link_submit.js" />"></script>
</head>
<body>

<nav role="navigation" class="navbar navbar-inverse">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a href="#" class="navbar-brand" id="brand">Java Mobile</a>
    </div>
    <!-- Collection of nav links and other content for toggling -->
    <ul class="nav navbar-nav">
        <li class="navbar-item" id="current-menu-item"><a href="#">Личные данные</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showContract' />" >Контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showTariffs' />" >Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showOptions' />" >Опции</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/index.jsp" />">Выйти</a></li>
    </ul>
</nav>

<div class="container" id="background">
    <div class="account-wall">
    <table class="table table-striped table-bordered table-condensed">
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
            <td>${client.dateOfBirth}</td>
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

</body>
</html>
