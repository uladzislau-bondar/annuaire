<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>
    <script type="text/javascript" src="../../resources/js/email.js"></script>

    <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
</head>
<body>
<form action="<c:url value="/email" />" method="post">
    <div class="container">
        <div class="card card-container">
            <h1>Отправка email</h1>

            <label for="emails">Кому:</label>
            <input id="emails" type="text" name="emails"
                   value="${emails}"><br>

            <label for="subject">Тема</label>
            <input id="subject" type="text" name="subject"/> <br>

            <label for="template">Шаблон:</label>
            <select id="template" onchange="changeTemplate(this.selectedIndex)">
                <option value="default">...</option>
                <option value="birthday">День рождения</option>
                <option value="christmas">Рождество</option>
            </select> <br>

            <c:forEach items="${templates}" var="template">
                <input type="hidden" id="${template.key}" name="${template.key}" value="${template.value}"/>
            </c:forEach>

            <label for="message">Текст сообщения:</label>
            <textarea id="message" name="message" ></textarea> <br>

            <button type="submit">Отправить</button>
            <a href="<c:url value="/" /> ">
                <button type="button">Отменить</button>
            </a><br>
        </div>
    </div>
</form>
</body>
</html>
