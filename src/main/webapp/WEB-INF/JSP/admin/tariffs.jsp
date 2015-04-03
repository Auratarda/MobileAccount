<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java Mobile</title>
    <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
    <%--custom styles--%>
    <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="/resources/js/link_submit.js" />"></script>
</head>
<body>

<nav role="navigation" class="navbar navbar-inverse">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a href="#" class="navbar-brand" id="brand">Java Mobile</a>
    </div>
    <!-- Collection of nav links and other content for toggling -->
    <ul class="nav navbar-nav">
        <li class="navbar-item"><a href="<c:url value='/admin/showAllContracts' />">Контракты</a></li>
        <li class="navbar-item" id="current-menu-item"><a href="#">Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/showAllOptions' />">Опции</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/assignNewContract' />">Заключить контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/search' />">Искать по номеру</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
    </ul>
</nav>

<div class="container">

    <table class="table table-striped table-bordered table-condensed" id="tariffs_table">
        <tr>
            <th>Тариф</th>
            <th>Цена</th>
            <th>Удалить</th>
        </tr>
        <c:forEach var="tariff" items="${allTariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.price}</td>
                <td>
                    <form method="post" action="<c:url value='/admin/removeTariff' />">
                        <input type="hidden" name="tariffName" value="${tariff.name}">
                        <input class="link" type="submit" value="Удалить">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

            <form class="form-signin" method="post" action="<c:url value='/admin/addNewTariff' />">
                <div class="form-group has-feedback has-feedback-left">
                    <label class="control-label">Название тарифа</label>
                    <input type="text" name='tariffName' class="form-control" placeholder="Придумайте название" required/>
                    <i class="form-control-feedback glyphicon glyphicon-phone-alt"></i>
                </div>
                <div class="form-group has-feedback has-feedback-left">
                    <label class="control-label">Цена</label>
                    <input type="text" name='tariffPrice' class="form-control" placeholder="Укажите стоимость" required/>
                    <i class="form-control-feedback glyphicon glyphicon-usd"></i>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                    Добавить новый тариф
                </button>
            </form>
        </div>
</body>
</html>