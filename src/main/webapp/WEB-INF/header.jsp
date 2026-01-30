<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<header>
  <nav>
    <div class="logo">
      <h1>Quest Pantera</h1>
    </div>
    <ul class="nav-links">
      <li><a href="${pageContext.request.contextPath}/home-page">Главная</a></li>
      <c:if test="${not empty sessionScope.username}">
        <li><a href="${pageContext.request.contextPath}/quest-dragon">Квест</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic-page">Статистика</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Выход (${sessionScope.username})</a></li>
      </c:if>
      <c:if test="${empty sessionScope.username}">
        <li><a href="${pageContext.request.contextPath}/login-page">Вход</a></li>
        <li><a href="${pageContext.request.contextPath}/register-page">Регистрация</a></li>
      </c:if>
    </ul>
  </nav>
</header>

