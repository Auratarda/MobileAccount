<%--
  Login page.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Java School Mobile Operator</title>
  <link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<div align="center">
  <h1>Добро пожаловать в Java School Mobile Operator!</h1>

  <form method="POST" action="/login">
    <table>
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
</body>
</html>
