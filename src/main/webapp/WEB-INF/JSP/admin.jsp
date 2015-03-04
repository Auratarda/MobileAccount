<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>

<div id="header">
    <h1>Администратор ${sessionScope.firstName}</h1>
</div>
<div id="nav">
    <div class="link">
        <a href="login.jsp">Login</a>
        <br>
        <a href="client.jsp">Profile</a>
    </div>
</div>
<div id="section">
    <jsp:include page="/WEB-INF/JSP/navBar.jsp"/>
    <table>
        <tr>
            <th>Номер</th>
            <th>Клиент</th>
        </tr>
        <c:forEach var="contract" items="${sessionScope.contracts}">
            <tr>
                <td>+7${contract.number}</td>
                <td>${contract.client}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="footer">
    <p>
        CreatedBy © Stanchin Denis & Vasilevskii Stanislav
    </p>
</div>
</body>
</html>