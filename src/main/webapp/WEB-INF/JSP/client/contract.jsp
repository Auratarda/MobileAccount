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
        <li class="navbar-item"><a href="<c:url value='/main/showPersonal' />">Личные данные</a></li>
        <li class="navbar-item" id="current-menu-item"><a href="<c:url value='/main/showContract' />">Контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showTariffs' />">Тарифы</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showOptions' />">Опции</a></li>
        <li class="navbar-item"><a href="<c:url value='/main/showBasket' />" >Корзина</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/index.jsp" />">Выйти</a></li>
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
                            <form action="<c:url value='/main/showOptions' />" method="get">
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
                            <form method="post" action="<c:url value='/main/removeOption' />">
                                <input type="hidden" name="optionName" value="${option.name}">
                                <input class="link" type="submit" value="Отключить">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <form method="get" action="<c:url value='/main/lockContractByClient' />">
                <button id="lock_button" class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                    Заблокировать номер
                </button>
            </form>
        </div>

</body>
</html>
