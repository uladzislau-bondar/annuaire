<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>

    <script type="text/javascript" src="../../resources/js/index.js"></script>
</head>
<body>

<c:set var="method" value="${param.method}" />

<c:choose>
    <c:when test="${method == 'show' or method == null}">
        <a href="<c:url value="/search" /> ">Поиск</a><br>
    </c:when>
    <c:when test="${method == 'search'}">
        <a href="<c:url value="/" /> ">На главную</a><br>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${empty contactList}">
        <!-- todo no contacts-->
    </c:when>
    <c:otherwise>
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
                        <td>
                            <a href="${editUrl}"><c:out value="${contact.fullName}"/></a>
                        </td>
                        <td><c:out value="${contact.dateOfBirth}"/></td>
                        <td><c:out value="${contact.address}"/></td>
                        <td><c:out value="${contact.placeOfWork}"/></td>
                        <td>
                            <a href="${editUrl}">Изменить</a></td>
                        <td>
                            <a href="${deleteUrl}">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <input id="deleteButton" type="button" value="Удалить" onclick="processSelected(this)">
            <input id="emailButton" type="button" value="Отправить email" onclick="processSelected(this)">
        </form>
    </c:otherwise>
</c:choose>

<a href="<c:url value="/contact" />">Создать</a>

<div class="pagination">
    <fmt:parseNumber var="pagesCount" type="number" value="${fn:length(contactList)/10}"/>
    <c:set var="currentPage" value="${empty param.offset ? 0 : param.offset}"/>

    <c:forEach begin="0" end="${pagesCount}" var="page">
        <c:choose>
            <c:when test="${method == 'show' or method == null}">
                <c:url var="url" value="/">
                    <c:param name="offset" value="${page*10}"/>
                    <c:param name="method" value="show"/>
                </c:url>
                <a href="${url}" <c:if test="${page eq currentPage}">class="active"</c:if>>
                    <c:out value="${page+1}"/></a>
            </c:when>
            <c:when test="${method == 'search'}">
                <c:url var="url" value="/search">
                    <c:param name="offset" value="${page*10}"/>
                    <c:param name="method" value="search"/>
                </c:url>
                <form action="${url}" method="post">
                    <!-- todo process post-->
                        <%--<a href="${url}" <c:if test="${page eq currentPage}">class="active"</c:if>>--%>
                        <%--<c:out value="${page+1}"/></a>--%>
                    <c:forEach items="${searchParams}" var="searchParam" >
                        <input type="hidden" name="${searchParam.key}" value="${searchParam.value}" />
                    </c:forEach>
                    <c:out value="${page+1}"/>
                    <input type="submit">
                </form>
            </c:when>
        </c:choose>
    </c:forEach>
</div>
</body>
</html>
