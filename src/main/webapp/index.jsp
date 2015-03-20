<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java School Mobile</title>
  <link rel="stylesheet" href="css/bootstrap/bootstrap.css" type="text/css">
  <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<header class="header">
  <p>Мобильный оператор Java School</p>
</header>

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
          <td colspan="0"><input type="submit" value="Войти" id="login"/></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<footer class="footer">
  <div class="container">
    <p class="text-vert">CreatedBy © Vasilevskii Stanislav</p>
  </div>
</footer>
</body>
</html>

