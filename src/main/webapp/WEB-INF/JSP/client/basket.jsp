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
    <li class="navbar-item"><a href="<c:url value='/main/showContract' />" >Контракт</a></li>
    <li class="navbar-item"><a href="<c:url value='/main/showTariffs' />" >Тарифы</a></li>
    <li class="navbar-item"><a href="<c:url value='/main/showOptions' />" >Опции</a></li>
    <li class="navbar-item" id="current-menu-item"><a href="<c:url value='/main/showBasket' />" >Корзина</a></li>
  </ul>
  <ul class="nav navbar-nav navbar-right">
    <li class="navbar-brand"><a href="<c:url value="/index.jsp" />">Выйти</a></li>
  </ul>
</nav>

<div class="container">
  <table class="table table-striped table-bordered table-condensed">
    <c:if test="${sessionScope.optionsInBasket.size()==0 && tariffInBasket.size()==0}">
      <tr>
        <td colspan="4">
          <form action="<c:url value='/main/showOptions' />" method="get">
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
      <th colspan="2">Убрать</th>
    </tr>
    <c:forEach var="tariff" items="${tariffInBasket}">
    <tr>
      <td>${tariff.name}</td>
      <td>${tariff.price}</td>
      <td colspan="2">
        <form method="post" action="<c:url value='/main/removeTariffFromBasket' />">
          <input type="submit" value="Убрать тариф">
        </form>
      </td>
      </c:forEach>
      </c:if>
    <c:if test="${sessionScope.optionsInBasket.size()!=0}">
    <tr>
      <th>Опция</th>
      <th>Цена</th>
      <th>Стоимость подключения</th>
      <th>Убрать</th>
    </tr>
    </c:if>
    <c:forEach var="option" items="${sessionScope.optionsInBasket}">
    <tr>
      <td>${option.name}</td>
      <td>${option.price}</td>
      <td>${option.connectionCost}</td>
      <td>
        <form method="post" action="<c:url value='/main/removeOptionFromBasket' />">
          <input type="hidden" name="optionName" value="${option.name}">
          <input type="submit" value="Убрать опцию">
        </form>
      </td>
      </c:forEach>
  </table>
<c:if test="${sessionScope.optionsInBasket.size()!=0 || tariffInBasket.size()!=0}">
  <form method="post" action="<c:url value='/main/buyItems' />">
    <button id="unlock_button" class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
      Купить
    </button>
  </form>
</c:if>
</div>

</body>
</html>


