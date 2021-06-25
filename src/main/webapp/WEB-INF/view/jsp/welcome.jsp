<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 03.06.2021
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
<div class="container my-5">

    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link active" href="/welcome">Welcome</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/tl/journeys">Маршруты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/tl/vehicles">Транспортные ср-ва</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/tl/stops">Остановки</a>
        </li>
    </ul>

    <h1>Welcome</h1>
    <div>
        <p class="text-justify">Hello Spring!</p>
    </div>
</div>
</body>
</html>
