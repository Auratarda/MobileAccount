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
  <li class="navbar-item"><a href="<c:url value='/admin/showAllTariffs' />">Тарифы</a></li>
  <li class="navbar-item" id="current-menu-item"><a href="#">Опции</a></li>
  <li class="navbar-item"><a href="<c:url value='/admin/assignNewContract' />">Заключить контракт</a></li>
  <li class="navbar-item"><a href="<c:url value='/admin/search' />">Искать по номеру</a></li>
  </ul>
  <ul class="nav navbar-nav navbar-right">
  <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
  </ul>
  </nav>

  <div class="container">
        <h1>${option.name}</h1>
    <table class="table table-striped table-bordered table-condensed" id="tariffs_table">
      <tr>
        <th>Требуемые опции</th>
      </tr>
      <c:forEach var="option" items="${requiredOptions}">
      <tr>
        <td>${option.name}</td>
        <td>
          <form method="post" action="<c:url value='#' />">
            <input type="hidden" name="optionName" value="${option.name}">
            <input class="link" type="submit" value="Удалить">
          </form>
        </td>
      </tr>
      </c:forEach>
    </table>

    <form class="form-signin" id="bottom-indent" method="post" action="<c:url value='#' />">
    <div class="form-group has-feedback has-feedback-left">
      <label class="control-label">Выберите опции</label>
      <div><select class="selectpicker" multiple size="5" name="selectedOptions[]">
        <c:forEach var="option" items="${allOptions}">
          <option value="${option.name}">${option.name}</option>
        </c:forEach>
      </select></div>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">
      Добавить требуемую опцию.
    </button>
    </form>
  </div>
  </body>
</html>