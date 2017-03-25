<%--
  Created by IntelliJ IDEA.
  User: colinforzeal
  Date: 8.3.17
  Time: 12.55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="<c:url value="/email"/>" method="post">
    Кому:
    <input type="text" name="emails"
    value="${emails}"/> <br>
    Тема:
    <input type="text" name="topic"/> <br>
    Шаблон:
    <input type="text" name="template"/> <br>
    Текст:
    <input type="text" name="text"/> <br>

    <input type="button" value="Отправить" />
    <input type="button" value="Отменить"/>
</form>
</body>
</html>
