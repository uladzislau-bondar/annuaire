<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: colinforzeal
  Date: 8.3.17
  Time: 12.59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${title}"</title>
</head>
<body>
<form action="<c:url value="/search" />" method="post">
    Имя:
    <input type="text" name="firstName"/> <br>
    Фамилия:
    <input type="text" name="lastName"/> <br>
    Отчество:
    <input type="text" name="middleName"/>"/> <br>
    <!-- todo further or earlier dateOfBirth -->
    Дата рождения:
    <input type="text" name="dateOfBirth"/> <br>
    Пол:
    <!-- todo c:if doesn't work -->
    <input type="radio" name="sex"
           value="MALE"
    <c:if test="${sex == 'MALE'}"> checked="checked"
    </c:if> >Мужчина</input>
    <input type="radio" name="sex"
           value="FEMALE"
    <c:if test="${sex == 'FEMALE'}"> checked="checked"
    </c:if> >Женщина</input> <br>
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
