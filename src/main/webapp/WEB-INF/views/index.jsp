<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Contact List</title>
</head>
<body>
<a href="search.jsp">
  <input type="button" value="Search">
</a>

<c:forEach items="${contactList}" var="contact">
  <input type="checkbox"/>
  <h1><a href="<c:url value="contact.jsp" />"><c:out value="${contact.fullName}"/></a></h1>
  <h2><c:out value="${contact.dateOfBirth}"/> -- <c:out value="${contact.address}"/> -- <c:out
          value="${contact.placeOfWork}"/></h2>
</c:forEach>

<a href="contact.jsp">
  <input type="button" value="Create">
</a>

<a href="email.jsp">
  <input type="button" value="Send email">
</a>

<input type="button" value="Delete">
</body>
</html>
