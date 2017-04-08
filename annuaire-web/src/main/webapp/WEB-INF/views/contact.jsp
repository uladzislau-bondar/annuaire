<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${title}"/></title>
    <script type="text/javascript" src="../../resources/js/contact.js"></script>
    <script type="text/javascript" src="../../resources/js/save.js"></script>
    <script type="text/javascript" src="../../resources/js/validate.js"></script>
    <script type="text/javascript" src="../../resources/js/phone.js"></script>
    <script type="text/javascript" src="../../resources/js/attachment.js"></script>
    <script type="text/javascript" src="../../resources/js/photo.js"></script>

    <link rel="stylesheet" type="text/css" href="../../resources/css/reset.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/containers.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/contact.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/table.css">
</head>
<body>

<c:set var="textPattern" value="^[a-zA-ZА-Яа-яЁё]+$"/>
<c:set var="textPatternWithSpaces" value="^[a-zA-ZА-Яа-яЁё\s]+$"/>
<c:set var="urlPattern" value="https?://.+"/>
<c:set var="zipPattern" value="[0-9]{6}"/>

<c:url value="/contact" var="postUrl">
    <c:if test="${param.id ne null}">
        <c:param name="id" value="${param.id}"/>
    </c:if>
</c:url>

<%@include file="header.jsp" %>

<form id="form" action="${postUrl}" method="post" enctype="multipart/form-data" onsubmit="save()">
    <div class="container">
        <div class="card card-container">
            <input type="file" id="photoInput" name="photo" onchange="changePhoto(this)"
                   accept="image/*" style="display: none;"/>
            <img id="photoImg" class="profile-img-card"
            <c:choose>
            <c:when test="${photoPath == null}">
                 src="../../resources/images/default-img.png"
            </c:when>
            <c:otherwise>
            <c:url value="/photo" var="photoUrl">
                <c:param name="id" value="${param.id}"/>
            </c:url>
                 src="${photoUrl}"
            </c:otherwise>
            </c:choose>
                 onclick="selectPhoto()"> <br>
            <fieldset>
                <legend><span class="number">1</span>Базовая информация</legend>

                <label for="firstName">Имя:</label>
                <input type="text" id="firstName" name="firstName"
                       value="<c:out value="${firstName}" />"
                       maxlength="32" pattern="${textPattern}"
                       required/> <br>

                <label for="lastName">Фамилия:</label>
                <input type="text" id="lastName" name="lastName"
                       value="<c:out value="${lastName}" />"
                       maxlength="32" pattern="${textPattern}"
                       required/> <br>

                <label for="middleName">Отчество:</label>
                <input type="text" id="middleName" name="middleName"
                       value="<c:out value="${middleName}"/>"
                       maxlength="32" pattern="${textPattern}"/> <br>

                <label for="dateOfBirth">Дата рождения:</label>
                <input type="text" id="dateOfBirth" name="dateOfBirth"
                       value="<c:out value="${dateOfBirth}" />"
                       pattern="\d{4}-\d{1,2}-\d{1,2}" placeholder="yyyy-mm-dd"/> <br>

                <label>Пол:</label>
                <c:choose>
                    <c:when test="${sex =='MALE'}">
                        <input type="radio" id="male" name="sex" value="MALE" checked="checked"/>
                        <label for="male" class="light">Мужчина</label><br>
                        <input type="radio" id="female" name="sex" value="FEMALE"/>
                        <label for="female" class="light">Женщина</label>
                    </c:when>
                    <c:otherwise>
                        <input type="radio" id="male" name="sex" value="MALE"/>
                        <label for="male" class="light">Мужчина</label><br>
                        <input type="radio" id="female" name="sex" value="FEMALE" checked="checked"/>
                        <label for="female" class="light">Женщина</label>
                    </c:otherwise>
                </c:choose>
            </fieldset>

            <fieldset>
                <legend><span class="number">2</span>Дополнительно</legend>

                <label for="citizenship">Гражданство:</label>
                <input type="text" id="citizenship" name="citizenship"
                       value="<c:out value="${citizenship}" />"
                       maxlength="32" pattern="${textPattern}"/> <br>

                <label for="maritalStatus">Семейное положение:</label>
                <input type="text" id="maritalStatus" name="maritalStatus"
                       value="<c:out value="${maritalStatus}" />"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="website">Вебсайт:</label>
                <input type="url" id="website" name="website"
                       placeholder="https://mywebsite.com" value="<c:out value="${website}" />"
                       pattern="${urlPattern}"/> <br>

                <label for="email">Электронная почта:</label>
                <input type="email" id="email" name="email"
                       value="<c:out value="${email}" />"/> <br>

                <label for="placeOfWork">Место работы:</label>
                <input type="text" id="placeOfWork" name="placeOfWork"
                       value="<c:out value="${placeOfWork}" />"
                       maxlength="32"/> <br>
            </fieldset>

            <fieldset>
                <legend><span class="number">3</span>Адрес</legend>

                <label for="country">Страна:</label>
                <input type="text" id="country" name="country"
                       value="<c:out value="${country}" />"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="city">Город:</label>
                <input type="text" id="city" name="city"
                       value="<c:out value="${city}" />"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="address">Адрес:</label>
                <input type="text" id="address" name="address"
                       value="<c:out value="${address}" />"
                       maxlength="64"/> <br>

                <label for="zip">Индекс:</label>
                <input type="text" id="zip" name="zip"
                       value="<c:out value="${zip}" />"
                       maxlength="10" pattern="${zipPattern}"/> <br>
            </fieldset>

            <button type="submit" class="btn btn-submit">Сохранить</button>
        </div>
    </div>

    <div class="table-container">
        <c:choose>
            <c:when test="${empty phones}">
                <h2 id="phoneTableLabel">Телефоны еще не созданы</h2>
            </c:when>
            <c:otherwise>
                <h2 id="phoneTableLabel">Список телефонов</h2>
            </c:otherwise>
        </c:choose>
        <table id="phonesTable" class="table table-hover table-mc-light-blue">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th class="text-left">Номер</th>
                <th class="text-left">Тип</th>
                <th class="text-left">Комментарий</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${phones}" var="phone">
                <tr id="phone${phone.id}">
                    <td><input type="hidden" value="existed"></td>
                    <td class="text-left"><input type="checkbox" name="selected" value="${phone.id}"></td>
                    <td class="text-left"><c:out value="${phone.number}"/></td>
                    <td class="text-left"><c:out value="${phone.type}"/></td>
                    <td class="text-left"><c:out value="${phone.comment}"/></td>
                    <td class="text-left"><input type="button" onclick="editPhone(this)" value="Изменить"></td>
                    <td class="text-left"><input type="button" onclick="deletePhone(this)" value="Удалить"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="btn-container left">
            <button type="button" class="btn btn-submit" onclick="openPhoneModal()">Создать</button>
            <button type="button" class="btn btn-cancel" onclick="deleteSelectedPhones()">Удалить</button>
        </div>
    </div>

    <div class="table-container">
        <c:choose>
            <c:when test="${empty attachments}">
                <h2 id="attachmentTableLabel">Присоединения еще не созданы</h2>
            </c:when>
            <c:otherwise>
                <h2 id="attachmentTableLabel">Список присоединений</h2>
            </c:otherwise>
        </c:choose>
        <table id="attachmentsTable" class="table table-hover table-mc-light-blue">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th class="text-left">Название</th>
                <th class="text-left">Дата загрузки</th>
                <th class="text-left">Комментарий</th>
                <th class="text-left"></th>
                <th class="text-left"></th>
            </tr>
            </thead>
            <tbody class="table-hover">
            <c:forEach items="${attachments}" var="attachment">
                <tr id="attachment${attachment.id}">
                    <c:url value="/attachment" var="attachmentUrl">
                        <c:param name="id" value="${attachment.id}"/>
                    </c:url>
                    <td><input type="hidden" value="existed"></td>
                    <td class="text-left"><input type="checkbox" name="selected" value="${attachment.id}"></td>
                    <td class="text-left">
                        <a href="${attachmentUrl}"><c:out value="${attachment.name}"/></a>
                    </td>
                    <td class="text-left"><c:out value="${attachment.dateOfUpload}"/></td>
                    <td class="text-left"><c:out value="${attachment.comment}"/></td>
                    <td><input type="hidden" name="fileName" value="${attachment.fileName}"></td>
                    <td class="text-left"><input type="button" onclick="editAttachment(this)" value="Изменить"></td>
                    <td class="text-left"><input type="button" onclick="deleteAttachment(this)" value="Удалить"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="btn-container left">
            <button type="button" class="btn btn-submit" onclick="openAttachmentModal()">Создать</button>
            <button type="button" class="btn btn-cancel" onclick="deleteSelectedAttachments()">Удалить</button>
        </div>
    </div>

    <div id="phoneModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closePhoneModal()">&times;</span>
            <input id="phoneHidden" type="hidden" name="hidden"/>
            <input id="phoneId" type="hidden" name="id"/>
            Код страны:
            <input id="phoneCountryCode" type="number" name="countryCode"/> <br>
            Код оператора:
            <input id="phoneOperatorCode" type="number" name="operatorCode"/> <br>
            Номер телефона:
            <input id="phoneNumber" type="number" name="number"/> <br>
            Тип телефона:
            <input id="phoneHomeRadio" type="radio" name="type"
                   value="HOME">Домашний</input>
            <input id="phoneMobileRadio" type="radio" name="type"
                   value="MOBILE">Мобильный</input><br>
            Комментарий:
            <input id="phoneComment" type="text" name="comment" maxlength="120"/> <br>

            <div class="btn-container">
                <button type="button" class="btn btn-submit" onclick="savePhone()">Сохранить</button>
                <button type="button" class="btn btn-cancel" onclick="closePhoneModal()">Отменить</button>
            </div>
        </div>
    </div>

    <div id="attachmentModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeAttachmentModal()">&times;</span>
            <input id="attachmentHidden" type="hidden" name="hidden"/>
            <input id="attachmentId" type="hidden" name="id"/>
            Название:
            <input id="attachmentName" type="text" name="name"/> <br>
            <input id="attachmentDateOfUpload" type="hidden" name="dateOfUpload"/>
            Комментарий:
            <input id="attachmentComment" type="text" name="comment" maxlength="120"/> <br>
            <input id="attachmentFileName" type="hidden" name="fileName">

            <div class="btn-container">
                <button type="button" class="btn btn-submit" onclick="saveAttachment()">Сохранить</button>
                <button type="button" class="btn btn-cancel" onclick="closeAttachmentModal()">Отменить</button>
            </div>
        </div>
    </div>
</form>

</body>
</html>