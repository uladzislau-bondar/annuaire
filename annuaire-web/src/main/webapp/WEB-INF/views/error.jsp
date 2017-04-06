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

<!-- todo create beatiful page -->
<div>
    <div>
        <div>
            <h1>Oops!</h1>
            <c:choose>
                <c:when test="${statusCode == 404}">
                    <h2>We can't seem to find the page on <${requestUri}</h2>
                </c:when>
                <c:otherwise>
                    <h2>Something went wrong.</h2>
                    <h2>Error code: <c:out value="${statusCode}" /> </h2>

                    <h4>Exception: <c:out value="${exception}" /></h4>
                    <h5>Message: <c:out value="${message}" /></h5>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-5 col-middle text-center">
            <img src="https://a0.muscache.com/airbnb/static/error_pages/404-Airbnb_final-d652ff855b1335dd3eedc3baa8dc8b69.gif" width="313" height="428" alt="Girl has dropped her ice cream.">
        </div>
    </div>
</div>
</div>

</body>
</html>
