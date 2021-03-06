<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java Mobile</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/bootstrap-select.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
    <%--custom styles--%>
    <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
    <%--js--%>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/link_submit.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-select.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script>
    <script type="text/javascript">
        $(document).ready(function (e) {
            $('.selectpicker').selectpicker();
        });
    </script>
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
        <li class="navbar-item" id="current-menu-item"><a href="<c:url value='/user/showContract' />">Контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showTariffs' />">Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showOptions' />">Опции</a></li>
        <li class="navbar-item"><a href="<c:url value='/user/showBasket' />">Корзина</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
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
        <c:if test="${options.size()!=0}">
            <tr>
                <th>Опция</th>
                <th>Цена</th>
                <th>Стоимость подключения</th>
                <th>Отключение</th>
            </tr>
        </c:if>
        <c:if test="${options.size()==0}">
            <tr>
                <td colspan="4">
                    <form action="<c:url value='/user/showOptions' />" method="get">
                        У Вас нет дополнительных опций.
                        <a href="javascript:;"
                           onclick="get_form(this).submit(); return false">Подключить</a>
                    </form>
                </td>
            </tr>
        </c:if>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>
                    <form method="post" action="<c:url value='/user/removeOption' />">
                        <input type="hidden" name="optionName" value="${option.name}">
                        <button class="btn btn-sm btn-warning" type="submit" name="submit">
                            Отключить опцию
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${client.contracts.size() > 1}">
        <form id="unlock_button" method="post" action="<c:url value='/user/showContract' />">
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Выберите номер для просмотра</label>

                <div><select class="selectpicker" name="contractNumber" required>
                    <c:forEach var="contract" items="${client.contracts}">
                        <option value="${contract.number}">+7${contract.number}</option>
                    </c:forEach>
                </select></div>
            </div>
            <button id="bottom-indent" class="btn btn-lg btn-success btn-block" type="submit" name="submit">
                Посмотреть контракт
            </button>
        </form>
    </c:if>

    <form method="get" action="<c:url value='/user/lockContractByClient' />">
        <button class="btn btn-lg btn-danger btn-block" type="submit" name="submit">
            Заблокировать номер
        </button>
    </form>
</div>

</body>
</html>
