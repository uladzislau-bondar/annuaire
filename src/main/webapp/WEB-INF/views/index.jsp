<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact List</title>
</head>
<body>
<a href="search.jsp">
    <input type="button" value="Search"> <br>
</a>

<c:forEach items="${contactList}" var="contact">
    <c:url value="/contact" var="url">
        <c:param name="id" value="${contact.id}"/>
    </c:url>
    <input type="checkbox"/> <br>
    <h1><a href="${url}"><c:out value="${contact.fullName}"/></a></h1>
    <h2><c:out value="${contact.dateOfBirth}"/> -- <c:out value="${contact.address}"/> -- <c:out
            value="${contact.placeOfWork}"/></h2>

    <a href="${url}">
        <input type="button" value="Edit"> <br>
    </a>

    <!-- add javascript for http delete processing -->
    <a href="${url}">
        <input type="button" value="Delete"> <br>
    </a>
</c:forEach>

<a href="<c:url value="/contact" />">
    <input type="button" value="Create">
</a>

<a href="email.jsp">
    <input type="button" value="Send email">
</a>

<input type="button" value="Delete">
</body>
</html>
