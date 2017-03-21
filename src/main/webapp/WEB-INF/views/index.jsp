<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact List</title>
</head>
<body>
<a href="<c:url value="/search" /> ">
    <input type="button" value="Поиск"> <br>
</a>

<table>
    <tr>
        <th></th>
        <th>Полное имя</th>
        <th>Дата рождения</th>
        <th>Адрес</th>
        <th>Компания</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${contactList}" var="contact">
        <tr>
            <c:url value="/contact" var="editUrl">
                <c:param name="id" value="${contact.id}"/>
            </c:url>
            <c:url value="/contact" var="deleteUrl">
                <c:param name="id" value="${contact.id}"/>
                <c:param name="method" value="delete"/>
            </c:url>

            <td><input type="checkbox"/></td>
            <td><a href="${editUrl}"><c:out value="${contact.fullName}"/></a></td>
            <td><c:out value="${contact.dateOfBirth}"/></td>
            <td><c:out value="${contact.address}"/></td>
            <td><c:out value="${contact.placeOfWork}"/></td>
            <td><a href="${editUrl}">
                <input type="button" value="Изменить">
            </a>
            </td>
            <!-- add javascript for http delete processing -->
            <td>
                <form action="${deleteUrl}" method="post">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="<c:url value="/contact" />">
    <input type="button" value="Create">
</a>

<a href="email.jsp">
    <input type="button" value="Send email">
</a>

<input type="button" value="Delete">
</body>
</html>
