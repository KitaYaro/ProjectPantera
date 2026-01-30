<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <title>Квесты</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/WEB-INF/header.jsp"/>

    <h2>Здесь начинается ваше приключение!"</h2>
    <h3>Выберите квест чтобы продолжить</h3>

    <form method="post" action="/quest-dragon">
        <input type="hidden" name="quest" value="the way of the dragon rider">
        <button type="submit">Как приручить дракона</button>
    </form>

<c:if test="${empty sessionScope.username}">
    <p style="color: #4099ff;">Зарегистрируйтесь/ войдите чтобы продолжить</p>
</c:if>

<p>${message}</p>
</body>
</html>
