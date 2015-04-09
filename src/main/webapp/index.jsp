<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java Mobile</title>
    <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
    <%--custom styles--%>
    <link href="<c:url value="/resources/css/javaMobile.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="background">
    <nav id="navigation-panel" class="navbar navbar-inverse" role="navigation">
        <div class="container-fluid">
            <div class='navbar-header'>
                <span class='navbar-brand' id="brand">Java Mobile</span>
            </div>
        </div>
    </nav>

    <div class="container">

                    <form class="form-signin" method="POST" action="<c:url value='/j_spring_security_check' />">
                        <div class="form-group has-feedback has-feedback-left">
                            <label class="control-label">Адрес электронной почты</label>
                            <input type="email" name='email' class="form-control" placeholder="email@mail.ru" required
                                   autofocus/>
                            <i class="form-control-feedback glyphicon glyphicon-user"></i>
                        </div>
                        <div class="form-group has-feedback has-feedback-left">
                            <label class="control-label">Пароль</label>
                            <input type="password" name='password' class="form-control" placeholder="password" required>
                            <i class="form-control-feedback glyphicon glyphicon-lock "></i>
                        </div>
                        <button class="btn btn-success btn-lg btn-block" type="submit" name="submit">
                            Войти
                        </button>
                    </form>
                </div>
            </div>

</body>
</html>