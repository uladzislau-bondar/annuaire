function openPhoneModal() {
    var modal = document.getElementById('phoneModal');
    modal.style.display = "block";
}

function closePhoneModal() {
    var modal = document.getElementById('phoneModal');
    clearPhoneModal();
    modal.style.display = "none";
}

function editPhone(element) {
    var id = element.parentNode.parentNode.id.substring(5);

    var dto = parsePhoneFromWindow(id);
    var phone = dtoToPhone(dto);

    document.getElementById("phoneHidden").value = phone.hidden;
    document.getElementById("phoneId").value = phone.id;
    document.getElementById("phoneCountryCode").value = phone.countryCode;
    document.getElementById("phoneOperatorCode").value = phone.operatorCode;
    document.getElementById("phoneNumber").value = phone.number;
    if (phone.type == document.getElementById("phoneHomeRadio").value){
        document.getElementById("phoneHomeRadio").setAttribute("checked", "checked");
    } else if (phone.type == document.getElementById("phoneMobileRadio").value){
        document.getElementById("phoneMobileRadio").setAttribute("checked", "checked");
    }
    document.getElementById("phoneComment").value = phone.comment;

    openPhoneModal();
}

function savePhone() {
    if (validatePhoneModal()){
        var id = document.getElementById("phoneId").value;
        if (id != '') {
            updatePhone(id);
        } else {
            createNewPhone();
        }

        closePhoneModal();
        showPhoneTable();
    }
}

function createNewPhone() {
    var phone = parsePhoneFromModal();
    var phoneDto = phoneToDto(phone);

    phoneDto.id = generateId();

    appendAddedPhoneRow(phoneDto);
}

function clearPhoneModal() {
    document.getElementById("phoneHidden").value = "";
    document.getElementById("phoneId").value = "";
    document.getElementById("phoneCountryCode").value = "";
    document.getElementById("phoneOperatorCode").value = "";
    document.getElementById("phoneNumber").value = "";
    document.getElementById("phoneHomeRadio").removeAttribute("checked");
    document.getElementById("phoneMobileRadio").removeAttribute("checked");
    document.getElementById("phoneComment").value = "";
}

function updatePhone(id) {
    var phone = parsePhoneFromModal();
    var dto = phoneToDto(phone);
    if (dto.hidden == 'existed') {
        dto.hidden = 'updated';
    }

    var phoneRows = document.getElementById("phone" + id).children;
    phoneRows[0].getElementsByTagName("input")[0].value = dto.hidden;
    phoneRows[1].getElementsByTagName("input")[0].value = dto.id;
    phoneRows[2].innerHTML = dto.number;
    phoneRows[3].innerHTML = dto.type;
    phoneRows[4].innerHTML = dto.comment;
}

function deletePhone(element) {
    var id = element.parentNode.parentNode.id.substring(5);
    deletePhoneById(id);
}

function deletePhoneById(id) {
    var type = document.getElementById("phone" + id).children[0].getElementsByTagName("input")[0].value;

    if (type == 'added') {
        deletePhoneRow(id);
    } else {
        deleteExistedPhone(id);
    }

    hidePhoneTableIfEmpty();
}

function deleteSelectedPhones() {
    var rows = Array.prototype.slice.call(document.getElementById("phonesTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    rows.forEach(function (row) {
        var checkBox = row.getElementsByTagName("td")[1].getElementsByTagName("input")[0];
        var id = checkBox.value;

        if (checkBox.checked) {
            deletePhoneById(id);
        }
    });

    hidePhoneTableIfEmpty();
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

function parsePhoneFromModal() {
    var phone = {};
    phone.hidden = document.getElementById("phoneHidden").value;
    phone.id = document.getElementById("phoneId").value;
    phone.countryCode = document.getElementById("phoneCountryCode").value;
    phone.operatorCode = document.getElementById("phoneOperatorCode").value;
    phone.number = document.getElementById("phoneNumber").value;
    if (document.getElementById("phoneHomeRadio").checked) {
        phone.type = document.getElementById("phoneHomeRadio").value;
    } else if (document.getElementById("phoneMobileRadio").checked) {
        phone.type = document.getElementById("phoneMobileRadio").value;
    } else{
        phone.type = "";
    }
    phone.comment = document.getElementById("phoneComment").value;

    return phone;
}

function parsePhoneFromWindow(id) {
    var dto = {};
    var phoneRows = document.getElementById("phone" + id).children;
    dto.hidden = phoneRows[0].getElementsByTagName("input")[0].value;
    dto.id = id;
    dto.number = phoneRows[2].innerHTML;
    dto.type = phoneRows[3].innerHTML;
    dto.comment = phoneRows[4].innerHTML;

    return dto;
}


function appendAddedPhoneRow(phone) {
    var tr = document.createElement("tr");
    tr.setAttribute("id", "phone" + phone.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td class='text-left'><input type='checkbox' name='selected' value=" + phone.id + "></td>" +
        "<td class='text-left'>" + phone.number + "</td>" +
        "<td class='text-left'>" + phone.type + "</td>" +
        "<td class='text-left'>" + phone.comment + "</td>" +
        "<td class='text-left'><button type='button' class='btn btn-submit' onclick='editPhone(this)'>Изменить</button></td>" +
        "<td class='text-left'><button type='button' class='btn btn-cancel' onclick='deletePhone(this)'>Удалить</button></td>";
    document.getElementById("phonesTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function phoneToDto(phone) {
    var dto = {};
    dto.hidden = phone.hidden;
    dto.id = phone.id;
    dto.number = buildNumber(phone);
    dto.type = phone.type;
    dto.comment = phone.comment;

    return dto;
}

function dtoToPhone(dto) {
    var phone = {};
    phone.hidden = dto.hidden;
    phone.id = dto.id;
    phone.countryCode = parseCountryCode(dto);
    phone.operatorCode = parseOperatorCode(dto);
    phone.number = parseNumber(dto);
    phone.type = dto.type;
    phone.comment = dto.comment;

    return phone;
}

function generateId() {
    return (new Date()).getTime();
}

function parseCountryCode(phone) {
    var values = phone.number.match(/\+(.*?)\-/);
    return values == null ? "" : values[1];
}

function parseOperatorCode(phone) {
    var values = phone.number.match(/\-(.*?)\-/);
    return values == null ? "" : values[1];
}

function parseNumber(phone) {
    var value = phone.number.substr(phone.number.lastIndexOf("-") + 1);
    return value == null ? "" : value;
}

function buildNumber(phone) {
    var result = "";
    var countryCode = trim(phone.countryCode);
    if (countryCode != ""){
        result += "+" + countryCode + "-";
    }
    var operatorCode = trim(phone.operatorCode);
    if (operatorCode != ""){
        result += operatorCode + "-";
    }
    var number = trim((phone.number));
    if (number != ""){
        result += number;
    }

    return result;
}

function trim(str) {
    return str.replace(/\s/g, '');
}

function hidePhoneTableIfEmpty() {
    var table = document.getElementById("phonesTable");
    if (table.getElementsByTagName("tbody")[0].children.length == 0){
        table.style.display = "none";

        var label = document.getElementById("phoneTableLabel");
        label.innerHTML = "Телефоны еще не созданы"
    }
}

function showPhoneTable() {
    var table = document.getElementById("phonesTable");
    table.style.display = "";

    var label = document.getElementById("phoneTableLabel");
    label.innerHTML = "Список телефонов"
}