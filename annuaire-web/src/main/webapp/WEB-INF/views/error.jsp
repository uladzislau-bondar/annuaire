<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>

    <link rel="stylesheet" type="text/css" href="../../resources/css/reset.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/navbar.css">
</head>
<body>

<%@include file="header.jsp" %>

<h1>Oops!</h1>
<h2>Something went wrong.</h2>
<h2>Error code: <c:out value="${statusCode}"/></h2>
<h2>Issue: <c:out value="${message}"/></h2>

</body>
</html>
