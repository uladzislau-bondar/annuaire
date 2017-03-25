<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../resources/js/attachment.js" ></script>
</head>
<body>
<input type="hidden" name="hidden" />
<input type="hidden" name="id" />
Название:
<input type="text" name="name" pattern="\d+"/> <br>
<input type="hidden" name="dateOfUpload" />
Комментарий:
<input type="text" name="comment" maxlength="64"/> <br>
Выберите файл:
<input type="file" name="file" />

<input type="button" value="Сохранить" onclick="saveAttachment()" />
<input type="button" value="Отменить" onclick="closePopup()"/>
</body>
</html>
