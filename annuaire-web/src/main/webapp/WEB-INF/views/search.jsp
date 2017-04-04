<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>

    <link rel="stylesheet" type="text/css" href="../../resources/css/main.css">
</head>
<body>

<c:set var="textPattern" value="^[a-zA-ZА-Яа-яЁё]+$"/>
<c:set var="textPatternWithSpaces" value="^[a-zA-ZА-Яа-яЁё\s]+$"/>
<c:set var="urlPattern" value="https?://.+"/>
<c:set var="zipPattern" value="[0-9]{6}"/>

<form action="<c:url value="/search" />" method="post">
    <div class="container">
        <div class="card card-container">
            <h1>Поиск</h1>

            <fieldset>
                <legend><span class="number">1</span>Базовая информация</legend>

                <label for="firstName">Имя:</label>
                <input type="text" id="firstName" name="firstName"
                       maxlength="32" pattern="${textPattern}"
                       required/> <br>

                <label for="lastName">Фамилия:</label>
                <input type="text" id="lastName" name="lastName"
                       maxlength="32" pattern="${textPattern}"
                       required/> <br>

                <label for="middleName">Отчество:</label>
                <input type="text" id="middleName" name="middleName"
                       maxlength="32" pattern="${textPattern}"/> <br>

                <label>Дата рождения:</label>
                <input type="radio" id="before" name="dateRadio" value="before"/>
                <label for="before" class="light">До</label>
                <input type="radio" id="after" name="dateRadio" value="after"/>
                <label for="after" class="light">После</label><br>
                <input type="text" id="dateOfBirth" name="dateOfBirth"
                       pattern="\d{4}-\d{1,2}-\d{1,2}" placeholder="yyyy-mm-dd"/> <br>

                <label>Пол:</label>
                <input type="radio" id="male" name="sex" value="MALE"/>
                <label for="male" class="light">Мужчина</label><br>
                <input type="radio" id="female" name="sex" value="FEMALE"/>
                <label for="female" class="light">Женщина</label>
            </fieldset>

            <fieldset>
                <legend><span class="number">2</span>Дополнительно</legend>

                <label for="citizenship">Гражданство:</label>
                <input type="text" id="citizenship" name="citizenship"
                       maxlength="32" pattern="${textPattern}"/> <br>

                <label for="maritalStatus">Семейное положение:</label>
                <input type="text" id="maritalStatus" name="maritalStatus"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="website">Вебсайт:</label>
                <input type="url" id="website" name="website"
                       placeholder="https://mywebsite.com"
                       pattern="${urlPattern}"/> <br>

                <label for="email">Электронная почта:</label>
                <input type="email" id="email" name="email"/> <br>

                <label for="placeOfWork">Место работы:</label>
                <input type="text" id="placeOfWork" name="placeOfWork"
                       maxlength="32"/> <br>
            </fieldset>

            <fieldset>
                <legend><span class="number">3</span>Адрес</legend>

                <label for="country">Страна:</label>
                <input type="text" id="country" name="country"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="city">Город:</label>
                <input type="text" id="city" name="city"
                       maxlength="32" pattern="${textPatternWithSpaces}"/> <br>

                <label for="address">Адрес:</label>
                <input type="text" id="address" name="address"
                       maxlength="64"/> <br>

                <label for="zip">Индекс:</label>
                <input type="text" id="zip" name="zip"
                       maxlength="10" pattern="${zipPattern}"/> <br>
            </fieldset>

            <button type="submit">Поиск</button>
            <a href="<c:url value="/" /> ">
                <button type="button">Отменить</button>
            </a><br>
        </div>
    </div>
</form>
</body>
</html>
