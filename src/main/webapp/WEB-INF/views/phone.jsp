<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../resources/js/phone.js" ></script>
</head>
<body>
<input type="hidden" name="hidden" />
<input type="hidden" name="id" />
Код страны:
<input type="text" name="countryCode" pattern="\d+"/> <br>
</body>
Номер телефона:
<input type="text" name="number" pattern="\d+"/> <br>
<!-- todo deal with radio -->
Тип телефона:
<input type="radio" name="type"
       value="HOME">Домашний</input>
<input type="radio" name="type"
       value="MOBILE">Мобильный</input> <br>
Комментарий:
<input type="text" name="comment" maxlength="64"/> <br>

<input type="button" value="Сохранить" onclick="savePhone()" />
<input type="button" value="Отменить" onclick="closePopup()"/>
</body>
</html>
