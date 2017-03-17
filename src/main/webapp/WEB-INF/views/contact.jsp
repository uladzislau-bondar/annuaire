<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <%--<c:import url="/WEB-INF/js/validate.js" />--%>
</head>
<body>

<!-- todo photo form -->

<form action="<c:url value="/contact" />" method="post">
    Имя:
    <input type="text" name="firstName"
           value="<c:out value="${firstName}" />" required /> <br>
    Фамилия:
    <input type="text" name="lastName"
           value="<c:out value="${lastName}" />" required/> <br>
    Отчество:
    <input type="text" name="middleName"
           value="<c:out value="${middleName}" />"/> <br>
    Дата рождения:
    <input type="text" name="dateOfBirth"
           value="<c:out value="${dateOfBirth}" />"/> <br>
    Пол:
    <input type="radio" name="sex"
           value="male">Мужчина</input>
    <input type="radio" name="sex"
           value="female">Женщина</input> <br>
    Гражданство:
    <input type="text" name="citizenship"
           value="<c:out value="${citizenship}" />"/> <br>
    Семейное положение:
    <input type="text" name="maritalStatus"
           value="<c:out value="${maritalStatus}" />"/> <br>
    Website:
    <input type="text" name="website"
           value="<c:out value="${website}" />"/> <br>
    Email:
    <input type="text" name="email"
           value="<c:out value="${email}" />"/> <br>
    Место работы:
    <input type="text" name="placeOfWork"
           value="<c:out value="${placeOfWork}" />"/> <br>
    Страна:
    <input type="text" name="country"
           value="<c:out value="${country}" />"/> <br>
    Город:
    <input type="text" name="city"
           value="<c:out value="${city}" />"/> <br>
    Адрес:
    <input type="text" name="address"
           value="<c:out value="${address}" />"/> <br>
    Индекс:
    <input type="text" name="zip"
           value="<c:out value="${zip}" />"/> <br>

    <input type="submit"/>
</form>

<!-- todo phones table -->
<!-- todo phones button to popup -->

<!-- todo attachments table -->
<!-- todo attachments button to popup -->

</body>
</html>
