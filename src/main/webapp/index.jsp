<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/JSP/header.jsp" %>
<html>
<head>
  <title>Java Mobile</title>
</head>
<body>
<nav id="navigation-panel" class="navbar navbar-inverse" role="navigation">
  <div class="container-fluid">
    <div class='navbar-header'>
      <span class='navbar-brand' id="brand">Java Mobile</span>
    </div>
  </div>
</nav>

<div class="container">
  <div class="row">
    <div class="col-sm-6 col-md-4 col-md-offset-4">
      <div class="account-wall">


        <form class="form-signin" method="POST" action="<c:url value='/main/login' />">
          <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 10px;">
            <label class="control-label">Адрес электронной почты</label>
            <input type="text" name='email' class="form-control" placeholder="email@mail.ru" required
                   autofocus/>
            <i class="form-control-feedback glyphicon glyphicon-user"></i>
          </div>
          <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 20px;">
            <label class="control-label">Пароль</label>
            <input type="password" name='password' class="form-control" placeholder="password" required>
            <i class="form-control-feedback glyphicon glyphicon-lock "></i>
          </div>
          <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
            Войти
          </button>
        </form>
      </div>
    </div>
  </div>
</div>

