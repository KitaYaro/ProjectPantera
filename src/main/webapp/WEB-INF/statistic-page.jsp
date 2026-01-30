<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>

  <title>Статистика</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/WEB-INF/header.jsp"/>

<h2>Ваша статистика</h2>

<table border="1">
  <tr>
    <th>Попытки</th>
    <th>Победы</th>
    <th>Поражения</th>
  </tr>
  <tr>
    <td>${statistic.attempts}</td>
    <td>${statistic.wins}</td>
    <td>${statistic.losses}</td>
  </tr>
</table>

<a href="/home-page">На главную</a>

</body>
</html>