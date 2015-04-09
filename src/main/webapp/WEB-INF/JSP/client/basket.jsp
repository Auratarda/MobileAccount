<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java Mobile</title>
  <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
  <%--custom styles--%>
  <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
  <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-select.js" />"></script>
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
    <li class="navbar-item"><a href="<c:url value='/user/showPersonal' />">Личные данные</a></li>
    <li class="navbar-item"><a href="<c:url value='/user/showContract' />" >Контракт</a></li>
    <li class="navbar-item"><a href="<c:url value='/user/showTariffs' />" >Тарифы</a></li>
    <li class="navbar-item"><a href="<c:url value='/user/showOptions' />" >Опции</a></li>
    <li class="navbar-item" id="current-menu-item"><a href="<c:url value='/user/showBasket' />" >Корзина</a></li>
  </ul>
  <ul class="nav navbar-nav navbar-right">
    <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
  </ul>
</nav>

<div class="container">

  <c:if test="${setTariffError == 'true'}">
    <div class="bs-example">
      <div class="alert alert-danger alert-error" id="notation">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        При подключении тарифа ${selectedTariff} недоступны опции:
        <c:forEach var="option" items="${unacceptableOptions}">
          "${option.name}" &nbsp;
        </c:forEach>
      </div>
    </div>
  </c:if>

  <table class="table table-striped table-bordered table-condensed">
    <c:if test="${sessionScope.optionsInBasket.size()==0 && tariffInBasket.size()==0}">
      <tr>
        <td colspan="4">
          <form action="<c:url value='/user/showOptions' />" method="get">
            Ваша корзина пуста.
            <a href="javascript:"
               onclick="get_form(this).submit(); return false">Подключить дополнительные опции</a>
          </form>
        </td>
      </tr>
    </c:if>
    <c:if test="${tariffInBasket.size()!=0}">
    <tr>
      <th>Тариф</th>
      <th>Цена</th>
      <th></th>
    </tr>
    <c:forEach var="tariff" items="${tariffInBasket}">
    <tr>
      <td>${tariff.name}</td>
      <td>${tariff.price}</td>
      <th></th>
      </c:forEach>
      </c:if>
    <c:if test="${sessionScope.optionsInBasket.size()!=0}">
    <tr>
      <th>Опция</th>
      <th>Цена</th>
      <th>Стоимость подключения</th>
    </tr>
    </c:if>
    <c:forEach var="option" items="${sessionScope.optionsInBasket}">
    <tr>
      <td>${option.name}</td>
      <td>${option.price}</td>
      <td>${option.connectionCost}</td>
      </c:forEach>
  </table>

<c:if test="${sessionScope.optionsInBasket.size()!=0 || tariffInBasket.size()!=0}">
  <div class="row">
    <div class="col-md-6">
      <form class="to_the_right" method="post" action="<c:url value='/user/clearBasket' />">
        <button class="btn btn-sm btn-danger near" type="submit" name="submit">
          Очистить корзину
        </button>
      </form>
    </div>
    <div class="col-md-6">
      <form method="post" action="<c:url value='/user/buyItems' />">
        <button class="btn btn-sm btn-success near" type="submit" name="submit">
          Купить
        </button>
      </form>
    </div>
  </div>
</c:if>
</div>

</body>
</html>


