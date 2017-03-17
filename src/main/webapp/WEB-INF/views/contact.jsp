<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <%--<c:import url="/WEB-INF/js/validate.js" />--%>
</head>
<body>

<!-- todo photo form -->

<c:url value="/contact" var="postUrl">
    <!-- todo test doesn't work -->
    <%--<c:if test="${param.id}">--%>
        <%--<c:param name="id" value="${param.id}"/>--%>
    <%--</c:if>--%>
</c:url>
<form action="${postUrl}" method="post">
    Имя:
    <input type="text" name="firstName"
           value="<c:out value="${firstName}" />" required/> <br>
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
           value="MALE"
    <c:if test="${sex == 'MALE'}"> checked="checked"
    </c:if> >Мужчина</input>
    <input type="radio" name="sex"
           value="FEMALE"
    <c:if test="${sex == 'FEMALE'}"> checked="checked"
    </c:if> >Женщина</input> <br>
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

<table>
    <tr>
        <th></th>
        <th>Номер</th>
        <th>Тип</th>
        <th>Комментарий</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${phones}" var="phone">
        <tr>
            <td><input type="checkbox"/></td>
            <td><c:out value="${phone.number}"/></td>
            <td><c:out value="${phone.type}"/></td>
            <td><c:out value="${phone.comment}"/></td>

            <!-- todo add phone editing and deleting -->
            <td><a href="#">
                <input type="button" value="Edit">
            </a>
            </td>
            <td><a href="#">
                <input type="button" value="Delete">
            </a>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- todo phones button to popup (creating) && deleting selected items -->

<!-- todo attachments table -->
<!-- todo attachments button to popup -->

</body>
</html>
