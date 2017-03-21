<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../resources/js/popup.js" ></script>
</head>
<body>
Код страны:
<input type="text" name="countryCode"
       value="<c:out value="${countryCode}" />"
       pattern="\d+"/> <br>
</body>
<!-- todo add operator code -->
Номер телефона:
<input type="text" name="number"
       value="<c:out value="${number}" />"
       pattern="\d+"/> <br>
Тип телефона:
<input type="radio" name="type"
       value="HOME"
<c:if test="${type == 'HOME'}"> checked="checked"
</c:if> >Домашний</input>
<input type="radio" name="type"
       value="MOBILE"
<c:if test="${type == 'MOBILE'}"> checked="checked"
</c:if> >Мобильный</input> <br>
Комментарий:
<input type="text" name="comment"
       value="<c:out value="${comment}" />"
       maxlength="64"/> <br>

<input type="button" value="Сохранить" onclick="createNewPhone()" />
<input type="button" value="Отменить" />
</body>
</html>
