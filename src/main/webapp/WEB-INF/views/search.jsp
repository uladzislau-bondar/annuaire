<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>
</head>
<body>
<form action="<c:url value="/search" />" method="post">
    Имя:
    <input type="text" name="firstName"/> <br>
    Фамилия:
    <input type="text" name="lastName"/> <br>
    Отчество:
    <input type="text" name="middleName"/> <br>
    Дата рождения:
    <input type="radio" name="dateRadio"
           value="before">Раньше</input>
    <input type="radio" name="dateRadio"
           value="after">Позднее</input> <br>
    <input type="text" name="dateOfBirth"/> <br>
    Пол:
    <input type="radio" name="sex"
           value="MALE">Мужчина</input>
    <input type="radio" name="sex"
           value="FEMALE">Женщина</input> <br>
    Гражданство:
    <input type="text" name="citizenship"/> <br>
    Семейное положение:
    <input type="text" name="maritalStatus"/> <br>
    Страна:
    <input type="text" name="country"/> <br>
    Город:
    <input type="text" name="city"/> <br>
    Адрес:
    <input type="text" name="address"/> <br>
    Индекс:
    <input type="text" name="zip"/> <br>

    <input type="submit"/>
</form>
</body>
</html>
