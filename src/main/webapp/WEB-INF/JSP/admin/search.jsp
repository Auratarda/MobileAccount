<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java Mobile</title>
  <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
  <%--custom styles--%>
  <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
  <%--scripts--%>
  <script type="text/javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
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
    <li class="navbar-item"><a href="<c:url value='/admin/showAllOptions' />">Опции</a></li>
    <li class="navbar-item"><a href="<c:url value='/admin/assignNewContract' />">Заключить контракт</a></li>
    <li class="navbar-item" id="current-menu-item"><a href="#">Искать по номеру</a></li>
  </ul>
  <ul class="nav navbar-nav navbar-right">
    <li class="navbar-brand"><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
  </ul>
</nav>

<div class="container">

  <form class="form-signin" method="post" action="<c:url value='/admin/searchByNumber' />">
    <div class="form-group has-feedback has-feedback-left">
      <label class="control-label">Введите номер телефона</label>
      <input type="text" name='searchNumber' class="form-control" placeholder="Например: 9041234567"
             maxlength="10" required/>
      <i class="form-control-feedback glyphicon glyphicon-phone-alt"></i>
      <c:if test="${notFound == 'Not found'}">
        <div class="bs-example">
          <div class="alert alert-danger alert-error" id="notation">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            Абонент ${searchNumber} не найден.
          </div>
        </div>
      </c:if>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
      Искать
    </button>
  </form>

</div>
</body>
</html>