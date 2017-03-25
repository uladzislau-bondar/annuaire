<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../resources/js/email.js" ></script>
</head>
<body>
<form action="<c:url value="/email" />" method="post">
    Кому:
    <input type="text" name="emails"
           value="${emails}"><br>
    Тема:
    <input type="text" name="topic"/> <br>
    Шаблон:
    <select onchange="changeTemplate(this.selectedIndex)">
        <option value="default">...</option>
        <option value="birthday">День рождения</option>
        <option value="christmas">Рождество</option>
    </select> <br>

    <c:forEach items="${templates}" var="template">
        <input type="hidden" name="${template.key}" value="${template.value}"/>
    </c:forEach>

    <textarea id="text">
    </textarea> <br>

    <input type="button" value="Отправить" />
    <input type="button" value="Отменить" />
</form>
</body>
</html>
