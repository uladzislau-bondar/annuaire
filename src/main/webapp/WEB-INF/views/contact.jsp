<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${title}" /></title>
</head>
<body>
<form action="<c:url value="/contact" />" method="post">
    <input type="text" name="firstName"/>
    <input type="text" name="lastName"/>
    <input type="text" name="middleName"/>
    <input type="text" name="placeOfWork"/>
    <input type="submit"/>
</form>
</body>
</html>
