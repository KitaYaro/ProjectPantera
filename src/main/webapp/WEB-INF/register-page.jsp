<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Регистрация</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/WEB-INF/header.jsp"/>

<h2>Регистрация</h2>
<c:if test="${not empty error}">
  <p style="color: #ff3535;">${error}</p>
</c:if>
<form method="post" action="/register-page">
  <p>
    <label>Имя пользователя:</label>
    <input type="text" name="username" required>
  </p>
  <p>
    <label>Пароль:</label>
    <input type="password" name="password" required>
  </p>
  <button type="submit">Зарегистрироваться</button>
</form>
<p><a href="/home-page">Назад</a></p>
</body>
</html>