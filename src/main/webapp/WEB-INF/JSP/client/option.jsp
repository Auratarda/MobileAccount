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
      <li>
        <form method="post" action="<c:url value='/main/showTariffs' />">
          <input type="hidden" name="contract" value="${contract.number}">
          <input class="myButton" type="submit" value="Тарифы">
        </form>
      </li>
      <li><input class="myButton" type="button" value="Опции"></li>
    </ul>
  </div>

  <div class="table" id="option">
    <table class="innerTable">
      <tr>
        <th>Опция</th>
        <th>Цена</th>
        <th>Стоимость подключения</th>
        <th>Добавление опции</th>
      </tr>
      <c:forEach var="option" items="${allOptions}">
        <tr>
          <td>${option.name}</td>
          <td>${option.price}</td>
          <td>${option.connectionCost}</td>
          <td>
            <form method="post" action="<c:url value='/main/addOption' />">
              <input type="hidden" name="contract" value="${contract.number}">
              <input type="hidden" name="optionName" value="${option.name}">
              <input class="link" type="submit" value="Подключить">
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