<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <script type="text/javascript" src="../../resources/js/save.js"></script>
    <script type="text/javascript" src="../../resources/js/popup.js"></script>
</head>
<body>

<!-- todo photo form -->

<c:url value="/contact" var="postUrl">
    <c:if test="${param.id ne null}">
        <c:param name="id" value="${param.id}"/>
    </c:if>
</c:url>
Имя:
<input type="text" name="firstName"
       value="<c:out value="${firstName}" />"
       maxlength="32" required/> <br>
Фамилия:
<input type="text" name="lastName"
       value="<c:out value="${lastName}" />"
       maxlength="32" required/> <br>
Отчество:
<input type="text" name="middleName"
       value="<c:out value="${middleName}"/>"
       maxlength="32"/> <br>
<!-- todo email validation -->
Дата рождения:
<input type="text" name="dateOfBirth"
       value="<c:out value="${dateOfBirth}" />"
       pattern="\d{4}-\d{1,2}-\d{1,2}" placeholder="yyyy-mm-dd"/> <br>
<!-- todo fix bug with radio buttons -->
Пол:
<c:choose>
    <c:when test="${sex =='MALE'}">
        <input type="radio" name="sex" value="MALE" checked="checked"/>Мужчина
        <input type="radio" name="sex" value="FEMALE" />Женщина <br>
    </c:when>
    <c:otherwise >
        <input type="radio" name="sex" value="MALE"/>Мужчина
        <input type="radio" name="sex" value="FEMALE" checked="checked"/>Женщина <br>
    </c:otherwise>
</c:choose>
<%--<input type="radio" name="sex"--%>
       <%--value="MALE"--%>
<%--<c:if test="${sex == 'MALE'}"> checked="checked"--%>
<%--</c:if> >Мужчина</input>--%>
<%--<input type="radio" name="sex"--%>
       <%--value="FEMALE"--%>
<%--<c:if test="${sex == 'FEMALE'}"> checked="checked"--%>
<%--</c:if> >Женщина</input> <br>--%>
Гражданство:
<input type="text" name="citizenship"
       value="<c:out value="${citizenship}" />"
       maxlength="32"/> <br>
Семейное положение:
<input type="text" name="maritalStatus"
       value="<c:out value="${maritalStatus}" />"
       maxlength="32"/> <br>
Website:
<input type="url" name="website"
       value="<c:out value="${website}" />"
       pattern="https?://.+"/> <br>
Email:
<input type="email" name="email"
       value="<c:out value="${email}" />"/> <br>
Место работы:
<input type="text" name="placeOfWork"
       value="<c:out value="${placeOfWork}" />"
       maxlength="32"/> <br>
Страна:
<input type="text" name="country"
       value="<c:out value="${country}" />"
       maxlength="32"/> <br>
Город:
<input type="text" name="city"
       value="<c:out value="${city}" />"
       maxlength="32"/> <br>
Адрес:
<input type="text" name="address"
       value="<c:out value="${address}" />"
       maxlength="64"/> <br>
Индекс:
<input type="text" name="zip"
       value="<c:out value="${zip}" />"
       pattern="\d+"/> <br>


<table id="phonesTable">
    <tr>
        <th></th>
        <th>Номер</th>
        <th>Тип</th>
        <th>Комментарий</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${phones}" var="phone">
        <c:url value="/phone" var="deleteUrl">
            <c:param name="id" value="${phone.id}"/>
            <c:param name="contactId" value="${id}"/>
            <c:param name="method" value="delete"/>
        </c:url>
        <tr id="phone${phone.id}">
            <td><input type="hidden" value="existed"></td>
            <td><input type="checkbox" name="selected" value="${phone.id}"></td>
            <td><c:out value="${phone.number}"/></td>
            <td><c:out value="${phone.type}"/></td>
            <td><c:out value="${phone.comment}"/></td>
            <td><input type="button" onclick="editPhone(this)" value="Изменить"></td>
            <td><input type="button" onclick="deletePhone(this)" value="Удалить"></td>
        </tr>
    </c:forEach>
</table>

<input type="button" onclick="showPhoneCreationPopup()" value="Создать телефон"/>

<table>
    <tr>
        <th></th>
        <th>Название</th>
        <th>Дата загрузки</th>
        <th>Комментарий</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${attachments}" var="attachment">
        <tr>
            <td><input type="checkbox"/></td>
            <td><c:out value="${attachment.name}"/></td>
            <td><c:out value="${attachment.dateOfUpload}"/></td>
            <td><c:out value="${attachment.comment}"/></td>

            <!-- todo add attachment editing and deleting -->
            <td><a href="#">
                <input type="button" value="Edit">
            </a>
            </td>
            <td><a href="#">
                <input type="button" value="Delete">
            </a>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- todo attachments button to popup -->

<input type="button" value="Сохранить" onclick="save('${postUrl}')"/>
</body>
</html>
