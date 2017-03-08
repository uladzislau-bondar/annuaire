<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Contact List</title>
  </head>
  <body>
    <c:forEach items="${contactList}" var="contact">
      <h1><c:out value="${contact.fullName}" /></h1>
      <h2><c:out value="${contact.dateOfBirth}" /> --  ${contact.address.address} -- <c:out value="${contact.placeOfWork}" /></h2>
    </c:forEach>
  </body>
</html>
