function showPhoneCreationPopup() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function showPhoneEditingPopup(element) {
    //todo refactor getting id
    var id = element.parentNode.parentNode.id.substring(5);
    var popup = window.open("/phone", "Edit phone", "width=800, height=800");
    popup.focus();
    popup.onload = function () {
        var dto = parsePhoneFromWindow(id);
        var phone = dtoToPhone(dto);
        popup.document.getElementsByName("id")[0].value = phone.id;
        popup.document.getElementsByName("countryCode")[0].value = phone.countryCode;
        popup.document.getElementsByName("number")[0].value = phone.number;
        popup.document.getElementsByName("type")[0].value = phone.type;
        popup.document.getElementsByName("comment")[0].value = phone.comment;
    }
}

function savePopup() {
    if (document.getElementsByName("id")[0].value != '') {
        var id = document.getElementsByName("id")[0].value;

        editPhone(id);
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

function editPhone(id) {
    if (window.opener != null) {
        var phone = parsePhoneFromPopup();
        var dto = phoneToDto(phone);
        window.opener.document.getElementById("phone" + id).children[1].getElementsByTagName("input")[0].value =
            dto.id;
        window.opener.document.getElementById("phone" + id).children[2].innerHTML = dto.number;
        window.opener.document.getElementById("phone" + id).children[3].innerHTML = dto.type;
        window.opener.document.getElementById("phone" + id).children[4].innerHTML = dto.comment;
    }
}

function closePopup() {
    window.close();
}

function parsePhoneFromPopup() {
    var phone = {};
    phone.id = document.getElementsByName("id")[0].value;
    phone.countryCode = document.getElementsByName("countryCode")[0].value;
    phone.number = document.getElementsByName("number")[0].value;
    phone.type = document.getElementsByName("type")[0].value;
    phone.comment = document.getElementsByName("comment")[0].value;

    return phone;
}

function parsePhoneFromWindow(id) {
    var dto = {};
    dto.id = id;
    dto.number = document.getElementById("phone" + id).children[2].innerHTML;
    dto.type = document.getElementById("phone" + id).children[3].innerHTML;
    dto.comment = document.getElementById("phone" + id).children[4].innerHTML;

    return dto;
}


//todo process deleting and editing
function appendAddedPhoneRow(phone) {
    var tr = window.opener.document.createElement("tr");
    tr.setAttribute("id", "phone" + phone.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td><input type='checkbox' name='selected' value="+ phone.id + "></td>" +
        "<td>" + phone.number + "</td>" +
        "<td>" + phone.type + "</td>" +
        "<td>" + phone.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='showPhoneEditingPopup(this)'></td>" +
        "<td><input type='button' value='Удалить' onclick='deleteRow()'></td>";
    window.opener.document.getElementById("phonesTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function deleteRow(id) {

}

function editRow(id) {

}

function phoneToDto(phone){
    var dto = {};
    dto.id = phone.id;
    dto.number = "+" + phone.countryCode + "-" + phone.number;
    dto.type = phone.type;
    dto.comment = phone.comment;

    return dto;
}

function dtoToPhone(dto) {
    var phone = {};
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
