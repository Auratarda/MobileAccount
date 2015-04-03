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
    <script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
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
        <li class="navbar-item"><a href="<c:url value='/admin/showAllContracts' />">Контракты</a></li>
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
        <th colspan="2" class="centered">Информация о клиенте</th>
    </tr>
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

    <table class="table table-striped table-bordered table-condensed">
        <tr>
            <th colspan="2" class="centered">Информация о контракте</th>
        </tr>
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
            <tr>
                <td><b>Состояние: </b></td>
                <td>${status}</td>
            </tr>
        </table>

    <form class="form-signin" method="post" action="<c:url value='/admin/setTariff' />">
        <div class="form-group has-feedback has-feedback-left">
            <label class="control-label">Выберите тариф</label>
            <div><select class="selectpicker" name="selectedTariff" required>
                <c:forEach var="tariff" items="${tariffs}">
                    <option value="${tariff.name}">${tariff.name}</option>
                </c:forEach>
            </select></div>
        </div>
        <input type="hidden" name="number" value=${contract.number}>
        <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
            Поменять тариф
        </button>
    </form>

    <table class="table table-striped table-bordered table-condensed">
        <tr>
            <th>Опция</th>
            <th>Цена</th>
            <th>Подключение</th>
            <th>Отключение</th>
        </tr>
        <c:forEach var="option" items="${options}">
            <tr>
                <td>${option.name}</td>
                <td>${option.price}</td>
                <td>${option.connectionCost}</td>
                <td>
                    <form method="post" action="<c:url value='/admin/removeOptionFromContract' />">
                        <input type="hidden" name="optionName" value="${option.name}">
                        <input type="hidden" name="number" value=${contract.number}>
                        <input class="link" type="submit" value="Отключить">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <form class="form-signin" method="post" action="<c:url value='/admin/addMoreOptions' />">
        <div class="form-group has-feedback has-feedback-left">
            <label class="control-label">Выберите опции</label>
            <div><select class="selectpicker" multiple size="5" name="selectedOptions[]">
                <c:forEach var="option" items="${allOptions}">
                    <option value="${option.name}">${option.name}</option>
                </c:forEach>
            </select></div>
        </div>
        <input type="hidden" name="number" value=${contract.number}>
        <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
            Добавить опции
        </button>
    </form>

    <c:if test="${status != 'Заблокирован оператором'}">
    <form method="post" action="<c:url value='/admin/lockContractByOperator' />">
        <input type="hidden" name="number" value=${contract.number}>
        <button id="lock_button" class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
            Заблокировать номер
        </button>
    </form>
    </c:if>

    <c:if test="${status == 'Заблокирован оператором'}">
        <form method="post" action="<c:url value='/admin/unlockContractByOperator' />">
            <input type="hidden" name="number" value=${contract.number}>
            <button id="unlock_button" class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                Разблокировать номер
            </button>
        </form>
    </c:if>

</div>
</body>
</html>
