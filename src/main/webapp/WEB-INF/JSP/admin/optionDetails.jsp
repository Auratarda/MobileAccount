<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java Mobile</title>
  <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
  <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/bootstrap-select.css"/>"/>
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
    <h3 class="text-center">Опция "${currentOption}"</h3>
    <table class="table table-striped table-bordered table-condensed" id="tariffs_table">
      <tr>
        <th colspan="2">Требуемые опции</th>
      </tr>
      <c:forEach var="option" items="${requiredOptions}">
      <tr>
        <td><form action="<c:url value='/admin/showOptionDetails' />" method="post">
          <a href="javascript:"
             onclick="get_form(this).submit(); return false">${option.name}</a>
          <input type="hidden" name="optionName" value="${option.name}">
        </form></td>
        <td>
          <form method="post" action="<c:url value='/admin/removeRequiredOption' />">
            <input type="hidden" name="currentOption" value="${currentOption}">
            <input type="hidden" name="optionToRemove" value="${option.name}">
            <input class="link" type="submit" value="Удалить">
          </form>
        </td>
      </tr>
      </c:forEach>
    </table>

    <form class="form-signin" id="bottom-indent" method="post" action="<c:url value='/admin/addRequiredOptions' />">
    <div class="form-group has-feedback has-feedback-left">
      <label class="control-label">Выберите опции</label>
      <div><select class="selectpicker" multiple size="5" name="selectedOptions[]">
        <c:forEach var="option" items="${addReqOptions}">
          <option value="${option.name}">${option.name}</option>
        </c:forEach>
      </select></div>
      <input type="hidden" name="optionName" value="${currentOption}">
    </div>
    <button class="btn btn-lg btn-success btn-block" type="submit">
      Добавить требуемые опции
    </button>
    </form>

    <table class="table table-striped table-bordered table-condensed">
      <tr>
        <th colspan="2">Несовместимые опции</th>
      </tr>
      <c:forEach var="option" items="${incompatibleOptions}">
        <tr>
          <td><form action="<c:url value='/admin/showOptionDetails' />" method="post">
            <a href="javascript:"
               onclick="get_form(this).submit(); return false">${option.name}</a>
            <input type="hidden" name="optionName" value="${option.name}">
          </form></td>
          <td>
            <form method="post" action="<c:url value='/admin/removeIncompatibleOption' />">
              <input type="hidden" name="currentOption" value="${currentOption}">
              <input type="hidden" name="optionToRemove" value="${option.name}">
              <input class="link" type="submit" value="Удалить">
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>

    <form class="form-signin" id="bottom-margin" method="post" action="<c:url value='/admin/addIncompatibleOption' />">
      <div class="form-group has-feedback has-feedback-left">
        <label class="control-label">Выберите опции</label>
        <div><select class="selectpicker" name="incOption">
          <c:forEach var="option" items="${addIncOptions}">
            <option value="${option.name}">${option.name}</option>
          </c:forEach>
        </select></div>
        <input type="hidden" name="optionName" value="${currentOption}">
      </div>
      <button class="btn btn-lg btn-success btn-block" type="submit">
        Добавить несовместимую опцию
      </button>
    </form>

  </div>
  </body>
</html>