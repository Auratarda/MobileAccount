<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Главная страница</title>
  <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
  <h1>Мобильный оператор Java School</h1>
</div>
<div id="nav">
  <div class="link">
  </div>
</div>

<div id="section">

  <div id="topNav"></div>

  <div>
    <form method="POST" action="login">
      <table class="loginTable">
        <tr>
          <td>Емейл:</td>
          <td><input type="email" name="email" size="26" required/><br/>
          </td>
        </tr>
        <tr>
          <td>Пароль:</td>
          <td><input type="password" name="password" size="26" required/><br/>
          </td>
        </tr>
        <tr>
          <td></td>
          <td><input type="submit" value="Войти" id="login"/></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<div id="loginError">Сожалеем, Ваш емаил или пароль введены неверно.<br>
  Попробуйте ввести данные еще раз!
</div>

<div id="footer">
  <p>
    CreatedBy © Vasilevskii Stanislav
  </p>
</div>
</body>
</html>

