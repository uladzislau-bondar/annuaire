<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <script type="text/javascript" src="../../resources/js/contact.js"></script>
    <script type="text/javascript" src="../../resources/js/save.js"></script>
    <script type="text/javascript" src="../../resources/js/phone.js"></script>
    <script type="text/javascript" src="../../resources/js/attachment.js"></script>
    <script type="text/javascript" src="../../resources/js/photo.js"></script>

    <link rel="stylesheet" type="text/css" href="../../resources/css/contact.css">
</head>
<body>

<c:url value="/contact" var="postUrl">
    <c:if test="${param.id ne null}">
        <c:param name="id" value="${param.id}"/>
    </c:if>
</c:url>
<form id="form" action="${postUrl}" method="post" enctype="multipart/form-data" onsubmit="save()">
    <!-- todo first head element of tables -->

    <input id="photoInput" type="fileName" name="photo" onchange="changePhoto(this);"
           accept="image/jpeg" style="display: none;"/>
    <img id="photo" src="../../resources/images/default-img.png" width="200" height="200" onclick="selectPhoto()"><br>

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
            <input type="radio" name="sex" value="FEMALE"/>Женщина <br>
        </c:when>
        <c:otherwise>
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
        <thead>
        <tr>
            <th></th>
            <th>Номер</th>
            <th>Тип</th>
            <th>Комментарий</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${phones}" var="phone">
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
        </tbody>
    </table>
    <input type="button" onclick="openPhoneModal()" value="Создать телефон"/> <br>
    <input type="button" onclick="deleteSelectedPhones()" value="Удалить выбранные"/> <br>

    <table id="attachmentsTable">
        <thead>
        <tr>
            <th></th>
            <th>Название</th>
            <th>Дата загрузки</th>
            <th>Комментарий</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${attachments}" var="attachment">
            <tr id="attachment${attachment.id}">
                <td><input type="hidden" value="existed"></td>
                <td><input type="checkbox" name="selected" value="${attachment.id}"></td>
                <td><c:out value="${attachment.name}"/></td>
                <td><c:out value="${attachment.dateOfUpload}"/></td>
                <td><c:out value="${attachment.comment}"/></td>
                <td><input type="hidden" name="fileName"></td>
                <td><input type="button" onclick="editAttachment(this)" value="Изменить"></td>
                <td><input type="button" onclick="deleteAttachment(this)" value="Удалить"></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input type="button" onclick="openAttachmentModal()" value="Создать присоединение"/> <br>
    <input type="button" onclick="deleteSelectedAttachments()" value="Удалить выбранные"/> <br>

    <div id="phoneModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closePhoneModal()">&times;</span>
            <input type="hidden" name="hidden"/>
            <input type="hidden" name="id"/>
            Код страны:
            <input type="text" name="countryCode" pattern="\d+"/> <br>
            Номер телефона:
            <input type="text" name="number" pattern="\d+"/> <br>
            <!-- todo deal with radio -->
            Тип телефона:
            <input type="radio" name="type"
                   value="HOME">Домашний</input>
            <input type="radio" name="type"
                   value="MOBILE">Мобильный</input><br>
            Комментарий:
            <input type="text" name="comment" maxlength="64"/> <br>

            <input type="button" value="Сохранить" onclick="savePhone()"/>
            <input type="button" value="Отменить" onclick="closePhoneModal()"/>
        </div>
    </div>

    <div id="attachmentModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeAttachmentModal()">&times;</span>
            <input type="hidden" name="hidden"/>
            <input type="hidden" name="id"/>
            Название:
            <input type="text" name="name" pattern="\d+"/> <br>
            <input type="hidden" name="dateOfUpload"/>
            Комментарий:
            <input type="text" id="attachmentComment" name="comment" maxlength="64"/> <br>
            <input type="hidden" id="attachmentFileName" name="fileName">
            <input type="button" value="Сохранить" onclick="saveAttachment()"/>
            <input type="button" value="Отменить" onclick="closeAttachmentModal()"/>
        </div>
    </div>

    <input type="submit"/>
</form>

</body>
</html>
