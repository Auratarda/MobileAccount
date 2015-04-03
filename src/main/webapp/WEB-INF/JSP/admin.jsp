<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <li class="navbar-item" id="current-menu-item"><a href="#">Контракты</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/showAllTariffs' />">Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/showAllOptions' />">Опции</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/assignNewContract' />">Заключить контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/search' />">Искать по номеру</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
    </ul>
</nav>

<div class="container">

    <table class="table table-striped table-bordered table-condensed">
        <tr>
            <th>Клиент</th>
            <th>Номер</th>
        </tr>
        <c:forEach var="contract" items="${allContracts}">
            <tr>
                <td>${contract.client}</td>
                <td>
                    <form action="<c:url value='/admin/showClient' />" method="post">
                        <a href="javascript:"
                           onclick="get_form(this).submit(); return false">+7${contract.number}</a>
                        <input type="hidden" name="number" value=${contract.number}>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%--<p class="lead">--%>
        <%--<sec:authorize access="hasRole('CLIENT')">--%>
            <%--<a href="<c:url value="/main/login"/> " class="btn btn-lg btn-default">Клиент</a>--%>
        <%--</sec:authorize>--%>
        <%--<sec:authorize access="hasRole('ADMIN')">--%>
            <%--<a href="<c:url value="/main/login"/> " class="btn btn-lg btn-default">Админ</a>--%>
        <%--</sec:authorize>--%>
    <%--</p>--%>
</div>
</body>
</html>
