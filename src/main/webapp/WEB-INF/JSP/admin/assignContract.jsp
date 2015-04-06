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
    <script>
        $(function () {
            $("#datepicker").datepicker();
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
        <li class="navbar-item" id="current-menu-item"><a href="#">Заключить контракт</a></li>
        <li class="navbar-item"><a href="<c:url value='/admin/search' />">Искать по номеру</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
    </ul>
</nav>

<div class="container">
        <form class="form-signin" id="bottom-indent" method="post" action="<c:url value='/admin/addNewClient' />">
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Имя</label>
                <input type="text" name='firstName' class="form-control" placeholder="Введите имя" required/>
                <i class="form-control-feedback glyphicon glyphicon-user"></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Фамилия</label>
                <input type="text" name='lastName' class="form-control" placeholder="Введите фамилию" required/>
                <i class="form-control-feedback glyphicon glyphicon-user"></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Паспортные данные</label>
                <input type="text" name='passport' class="form-control" placeholder="Введите паспортные данные"
                       required/>
                <i class="form-control-feedback glyphicon glyphicon-file"></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Адрес</label>
                <input type="text" name='address' class="form-control" placeholder="Введите адрес" required/>
                <i class="form-control-feedback glyphicon glyphicon-home"></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Адрес электронной почты</label>
                <input type="email" name='email' class="form-control" placeholder="email@mail.ru" required/>
                <i class="form-control-feedback glyphicon glyphicon-envelope"></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Пароль</label>
                <input type="password" name='password' class="form-control" placeholder="password" required>
                <i class="form-control-feedback glyphicon glyphicon-lock "></i>
            </div>
            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Дата Рождения</label>
                <input type="text" id="datepicker" name='dateOfBirth' class="form-control"
                       placeholder="Укажите дату рождения" required/>
                <i class="form-control-feedback glyphicon glyphicon-gift"></i>
            </div>

            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Выберите номер телефона</label>
                <div><select class="selectpicker" name="selectedNumber" required>
                    <c:forEach var="contract" items="${freeContracts}">
                        <option value="${contract.number}">+7${contract.number}</option>
                    </c:forEach>
                </select></div>
            </div>

            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Выберите тариф</label>
                <div><select class="selectpicker" name="selectedTariff" required>
                    <c:forEach var="tariff" items="${tariffs}">
                        <option value="${tariff.name}">${tariff.name}</option>
                    </c:forEach>
                </select></div>
            </div>

            <div class="form-group has-feedback has-feedback-left">
                <label class="control-label">Выберите опции</label>
                <div><select class="selectpicker" multiple size="5" name="selectedOptions[]">
                    <c:forEach var="option" items="${allOptions}">
                        <option value="${option.name}">${option.name}</option>
                    </c:forEach>
                </select></div>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">
                Заключить договор
            </button>
        </form>
</div>
</body>
</html>