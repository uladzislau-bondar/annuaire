<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${title}"/></title>

    <script type="text/javascript" src="../../resources/js/validate.js"></script>
    <script type="text/javascript" src="../../resources/js/save.js"></script>
    <script type="text/javascript" src="../../resources/js/index.js"></script>

    <link rel="stylesheet" type="text/css" href="../../resources/css/reset.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/table.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/containers.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/pagination.css">
</head>
<body>

<c:set var="method" value="${param.method}"/>

<%@include file="header.jsp" %>

<form id="contactsForm" action="<c:url value="/" />" method="post">
    <c:choose>
        <c:when test="${empty contactList}">
            <c:choose>
                <c:when test="${method == 'show' or method == null}">
                    <h1>Контакты еще не созданы</h1>
                </c:when>
                <c:when test="${method == 'search'}">
                    <h1>Ничего не найдено</h1>
                </c:when>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:url value="/" var="deleteSelectedUrl">
                <c:param name="method" value="delete"/>
            </c:url>

            <c:url value="/" var="emailSelectedUrl">
                <c:param name="method" value="email"/>
            </c:url>

            <c:choose>
                <c:when test="${method == 'show' or method == null}">
                    <h1>Список контактов</h1>
                </c:when>
                <c:when test="${method == 'search'}">
                    <h1>Результаты поиска</h1>
                </c:when>
            </c:choose>


            <table class="table table-hover table-mc-light-blue">
                <thead>
                <tr>
                    <th></th>
                    <th class="text-left">Полное имя</th>
                    <th class="text-left">Дата рождения</th>
                    <th class="text-left">Адрес</th>
                    <th class="text-left">Компания</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody class="table-hover">
                <c:forEach items="${contactList}" var="contact">
                    <tr>
                        <c:url value="/contact" var="editUrl">
                            <c:param name="id" value="${contact.id}"/>
                            <c:param name="method" value="show"/>
                        </c:url>
                        <c:url value="/contact" var="deleteUrl">
                            <c:param name="id" value="${contact.id}"/>
                            <c:param name="method" value="delete"/>
                        </c:url>

                        <td class="text-left">
                            <input type="checkbox" name="selected" value="${contact.id}"/>
                        </td>
                        <td class="text-left">
                            <a href="${editUrl}"><c:out value="${contact.fullName}"/></a>
                        </td>
                        <td class="text-left"><c:out value="${contact.dateOfBirth}"/></td>
                        <td class="text-left"><c:out value="${contact.address}"/></td>
                        <td class="text-left"><c:out value="${contact.placeOfWork}"/></td>
                        <td class="text-left">
                            <a href="${editUrl}">
                                <button type="button" class="btn btn-submit">Изменить</button>
                            </a>
                        </td>
                        <td class="text-left">
                            <a href="${deleteUrl}">
                                <button type="button" class="btn btn-cancel">Удалить</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <div class="btn-container">
        <a href="<c:url value="/contact" />">
            <button type="button" class="btn btn-submit">Создать</button>
        </a>
        <button id="emailButton" type="button" class="btn btn-email"
                onclick="processSelected(this)">Отправить email
        </button>
        <button id="deleteButton" type="button" class="btn btn-cancel"
                onclick="processSelected(this)">Удалить
        </button>
    </div>
</form>

<div class="pagination-wrapper">
    <fmt:parseNumber var="pagesCount" type="number" value="${fn:length(contactList)/10}"/>
    <c:set var="currentPage" value="${empty param.offset ? 0 : param.offset}"/>

    <ul class="pagination">
        <c:forEach begin="0" end="${pagesCount}" var="page">
            <li>
                <c:choose>
                    <c:when test="${method == 'show' or method == null}">
                        <c:url var="url" value="/">
                            <c:param name="offset" value="${page*10}"/>
                            <c:param name="method" value="show"/>
                        </c:url>
                        <span
                                <c:if test="${page eq currentPage}">class="active"</c:if>
                                onclick="paginate('${url}', 'show')">
                            <c:out value="${page+1}"/></span>
                    </c:when>
                    <c:when test="${method == 'search'}">
                        <input type="hidden" id="searchParams" value="${searchParams}"/>

                        <c:url var="url" value="/search">
                            <c:param name="offset" value="${page*10}"/>
                            <c:param name="method" value="search"/>
                        </c:url>
                        <span
                                <c:if test="${page eq currentPage}">class="active"</c:if>
                                onclick="paginate('${url}', 'search')">
                            <c:out value="${page+1}"/></span>
                        <%--<form action="${url}" method="post">--%>
                        <%--<!-- todo process post-->--%>
                        <%--&lt;%&ndash;<a href="${url}" <c:if test="${page eq currentPage}">class="active"</c:if>>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<c:out value="${page+1}"/></a>&ndash;%&gt;--%>
                        <%--<span <c:if test="${page eq currentPage}">class="active"</c:if>>--%>
                        <%--<c:out value="${page+1}"/>--%>
                        <%--</span>--%>
                        <%--<input type="submit">--%>
                        <%--</form>--%>
                    </c:when>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
