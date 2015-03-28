<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Личный кабинет</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css">
</head>
<body>

<div id="header">
  <h1>Личный кабинет (${client.firstName} ${client.lastName})</h1>
</div>
<div id="nav">
  <div class="link">
    <a href="<c:url value="/index.jsp" />">Выйти</a>
  </div>
</div>

<div id="section">

  <div id="topNav">
    <ul>
      <li>
        <form method="post" action="<c:url value='/main/showPersonal' />">
          <input type="hidden" name="contract" value="${contract.number}">
          <input class="myButton" type="submit" value="Личные данные">
        </form>
      </li>
      <li>
        <form method="post" action="<c:url value='/main/showContract' />">
          <input type="hidden" name="contract" value="${contract.number}">
          <input class="myButton" type="submit" value="Контракт">
        </form>
      </li>
      <li><input class="myButton" type="button" value="Тарифы"></li>
      <li>
        <form method="post" action="<c:url value='/main/showOptions' />">
          <input type="hidden" name="contract" value="${contract.number}">
          <input class="myButton" type="submit" value="Опции">
        </form>
      </li>
    </ul>
  </div>

  <div class="table" id="tariffs">
    <table class="innerTable">
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
            <form method="post" action="<c:url value='/main/changeTariff' />">
              <input type="hidden" name="contract" value="${contract.number}">
              <input type="hidden" name="tariffName" value="${tariff.name}">
              <input class="link" type="submit" value="Перейти">
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>

<div id="footer">
  <p>
    CreatedBy © Vasilevskii Stanislav
  </p>
</div>
</body>
</html>
