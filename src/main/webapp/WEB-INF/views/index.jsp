<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact List</title>
    <script type="text/javascript" src="../../resources/js/index.js"></script>
</head>
<body>
<a href="<c:url value="/search" /> ">
    <input type="button" value="Поиск"> <br>
</a>

<c:url value="/" var="deleteSelectedUrl">
    <c:param name="method" value="delete"/>
</c:url>
<c:url value="/" var="emailSelectedUrl">
    <c:param name="method" value="email"/>
</c:url>

<form id="contactsForm" action="<c:url value="/" />" method="post">
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

                <td>
                    <input type="checkbox" name="selected" value="${contact.id}"/>
                </td>
                <td><a href="${editUrl}"><c:out value="${contact.fullName}"/></a></td>
                <td><c:out value="${contact.dateOfBirth}"/></td>
                <td><c:out value="${contact.address}"/></td>
                <td><c:out value="${contact.placeOfWork}"/></td>
                <td><a href="${editUrl}">
                    <input type="button" value="Изменить">
                </a>
                </td>
                <td>
                    <a href="${deleteUrl}">
                        <input type="button" value="Удалить">
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <input id="deleteButton" type="button" value="Удалить" onclick="processSelected(this)">
    <input id="emailButton" type="button" value="Отправить email" onclick="processSelected(this)">
</form>

<a href="<c:url value="/contact" />">
    <input type="button" value="Create">
</a>

<div class="pagination">
    <fmt:parseNumber var="pagesCount" type="number" value="${fn:length(contactList)/10}"/>
    <c:set var="currentPage" value="${empty param.offset ? 0 : param.offset}"/>

    <c:forEach begin="0" end="${pagesCount}" var="page">
        <c:url var="url" value="/">
            <c:param name="offset" value="${page*10}"/>
        </c:url>
        <a href="${url}" <c:if test="${page eq currentPage}">class="active"</c:if>>
        <c:out value="${page+1}"/></a>
    </c:forEach>
</div>
</body>
</html>
