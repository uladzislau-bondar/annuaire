function showPhoneCreationPopup() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function editPhone(element) {
    //todo refactor getting id
    var id = element.parentNode.parentNode.id.substring(5);
    var popup = window.open("/phone", "Edit phone", "width=800, height=800");
    popup.focus();
    popup.onload = function () {
        var dto = parsePhoneFromWindow(id);
        var phone = dtoToPhone(dto);
        popup.document.getElementsByName("hidden")[0].value = phone.hidden;
        popup.document.getElementsByName("id")[0].value = phone.id;
        popup.document.getElementsByName("countryCode")[0].value = phone.countryCode;
        popup.document.getElementsByName("number")[0].value = phone.number;
        popup.document.getElementsByName("type")[0].value = phone.type;
        popup.document.getElementsByName("comment")[0].value = phone.comment;
    }
}

function savePhone() {
    if (document.getElementsByName("id")[0].value != '') {
        var id = document.getElementsByName("id")[0].value;

        updatePhone(id);
    } else {
        createNewPhone();
    }
    window.close();
}

function createNewPhone() {
    if (window.opener != null) {
        var phone = parsePhoneFromPopup();
        var phoneDto = phoneToDto(phone);
        phoneDto.id = generateId();
        appendAddedPhoneRow(phoneDto);
    }
}

function updatePhone(id) {
    if (window.opener != null) {
        var phone = parsePhoneFromPopup();
        var dto = phoneToDto(phone);
        if (dto.hidden == 'existed'){
            dto.hidden = 'updated';
        }
        window.opener.document.getElementById("phone" + id).children[0].getElementsByTagName("input")[0].value =
            dto.hidden;
        window.opener.document.getElementById("phone" + id).children[1].getElementsByTagName("input")[0].value =
            dto.id;
        window.opener.document.getElementById("phone" + id).children[2].innerHTML = dto.number;
        window.opener.document.getElementById("phone" + id).children[3].innerHTML = dto.type;
        window.opener.document.getElementById("phone" + id).children[4].innerHTML = dto.comment;
    }
}

function deletePhone(element) {
    var id = element.parentNode.parentNode.id.substring(5);
    deletePhoneById(id);
}

function deletePhoneById(id) {
    var type = document.getElementById("phone" + id).children[0].getElementsByTagName("input")[0].value;

    if (type == 'added'){
        deletePhoneRow(id);
    } else {
        deleteExistedPhone(id);
    }
}

function deleteSelectedPhones() {
    var rows = Array.prototype.slice.call(document.getElementById("phonesTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    rows.forEach(function (row) {
        var checkBox = row.getElementsByTagName("td")[1].getElementsByTagName("input")[0];
        var id = checkBox.value;

        if (checkBox.checked){
            deletePhoneById(id);
        }
    })
}

function deletePhoneRow(id) {
    document.getElementById("phone" + id).outerHTML = "";
}

function deleteExistedPhone(id) {
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "phoneToDelete");
    input.setAttribute("value", id);
    document.body.appendChild(input);

    deletePhoneRow(id);
}

function closePopup() {
    window.close();
}

function parsePhoneFromPopup() {
    var phone = {};
    phone.hidden = document.getElementsByName("hidden")[0].value;
    phone.id = document.getElementsByName("id")[0].value;
    phone.countryCode = document.getElementsByName("countryCode")[0].value;
    phone.number = document.getElementsByName("number")[0].value;
    phone.type = document.getElementsByName("type")[0].value;
    phone.comment = document.getElementsByName("comment")[0].value;

    return phone;
}

function parsePhoneFromWindow(id) {
    var dto = {};
    dto.hidden = document.getElementById("phone" + id).children[0].getElementsByTagName("input")[0].value;
    dto.id = id;
    dto.number = document.getElementById("phone" + id).children[2].innerHTML;
    dto.type = document.getElementById("phone" + id).children[3].innerHTML;
    dto.comment = document.getElementById("phone" + id).children[4].innerHTML;

    return dto;
}


function appendAddedPhoneRow(phone) {
    var tr = window.opener.document.createElement("tr");
    tr.setAttribute("id", "phone" + phone.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td><input type='checkbox' name='selected' value="+ phone.id + "></td>" +
        "<td>" + phone.number + "</td>" +
        "<td>" + phone.type + "</td>" +
        "<td>" + phone.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='editPhone(this)'></td>" +
        "<td><input type='button' value='Удалить' onclick='deletePhone(this)'></td>";
    window.opener.document.getElementById("phonesTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function phoneToDto(phone){
    var dto = {};
    dto.hidden = phone.hidden;
    dto.id = phone.id;
    dto.number = "+" + phone.countryCode + "-" + phone.number;
    dto.type = phone.type;
    dto.comment = phone.comment;

    return dto;
}

function dtoToPhone(dto) {
    var phone = {};
    phone.hidden = dto.hidden;
    phone.id = dto.id;
    phone.countryCode = dto.number.match(/\+(.*)\-/)[1];
    phone.number = dto.number.substr(dto.number.indexOf("-") + 1);
    phone.type = dto.type;
    phone.comment = dto.comment;

    return phone;
}

function generateId() {
    return (new Date()).getTime();
}
