<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java School Mobile</title>
  <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.css" type="text/css">
  <link rel="stylesheet" href="resources/css/index.css" type="text/css">
  <link rel="stylesheet" href="resources/css/login.css" type="text/css">
</head>
<body>
<header class="header">
  <p>Мобильный оператор Java School</p>
</header>

<div class="container">

  <form class="form-signin" method="POST" action="<c:url value='/main/login' />">
    <h2 class="form-signin-heading">Вход в личный кабинет</h2>
    <label for="inputEmail" class="sr-only">Адрес электронной почты</label>
    <input type="email" id="inputEmail" name="email" size="25" class="form-control"
           placeholder="Адрес электронной почты" required autofocus>
    <label for="inputPassword" class="sr-only">Пароль</label>
    <input type="password" id="inputPassword" name="password" size="25" class="form-control" placeholder="Пароль"
           required>

    <div class="checkbox">
      <label>
        <input type="checkbox" value="remember-me"> Запомнить меня
      </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
  </form>

</div>


<footer class="footer">
  <div class="container">
    <p class="text-vert">CreatedBy © Vasilevskii Stanislav</p>
  </div>
</footer>
</body>
</html>

