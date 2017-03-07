<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Contact List</title>
  </head>
  <body>
    <h1>${contact.firstName}  ${contact.lastName}  ${contact.email}</h1>
    <c:out value="${contact.firstName}" />
  </body>
</html>
