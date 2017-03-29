function editPhone(element) {
    var id = element.parentNode.parentNode.id.substring(5);

    var dto = parsePhoneFromWindow(id);
    var phone = dtoToPhone(dto);

    var modal = document.getElementById('phoneModal');
    var inputs = modal.getElementsByTagName("input");
    inputs[0].value = phone.hidden;
    inputs[1].value = phone.id;
    inputs[2].value = phone.countryCode;
    inputs[3].value = phone.number;
    if (phone.type == inputs[4].value){
        inputs[4].setAttribute("checked", "checked");
    } else if (phone.type == inputs[5].value){
        inputs[5].setAttribute("checked", "checked");
    }
    inputs[6].value = phone.comment;

    modal.style.display = "block";
}

function savePhone() {
    var modal = document.getElementById('phoneModal');
    if (modal.getElementsByTagName("input")[1].value != '') {
        var id = modal.getElementsByTagName("input")[1].value;

        updatePhone(id);
    } else {
        createNewPhone();
    }

    clearPhoneModal();
    modal.style.display = "none";
}

function createNewPhone() {
    var phone = parsePhoneFromModal();
    var phoneDto = phoneToDto(phone);
    phoneDto.id = generateId();
    appendAddedPhoneRow(phoneDto);
}

function clearPhoneModal() {
    var inputs = document.getElementById('phoneModal').getElementsByTagName("input");
    inputs[0].value = "";
    inputs[1].value = "";
    inputs[2].value = "";
    inputs[3].value = "";
    inputs[4].removeAttribute("checked");
    inputs[5].removeAttribute("checked");
    inputs[6].value = "";
}

function updatePhone(id) {
    var phone = parsePhoneFromModal();
    var dto = phoneToDto(phone);
    if (dto.hidden == 'existed') {
        dto.hidden = 'updated';
    }
    document.getElementById("phone" + id).children[0].getElementsByTagName("input")[0].value =
        dto.hidden;
    document.getElementById("phone" + id).children[1].getElementsByTagName("input")[0].value =
        dto.id;
    document.getElementById("phone" + id).children[2].innerHTML = dto.number;
    document.getElementById("phone" + id).children[3].innerHTML = dto.type;
    document.getElementById("phone" + id).children[4].innerHTML = dto.comment;
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
}

function deleteSelectedPhones() {
    var rows = Array.prototype.slice.call(document.getElementById("phonesTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    rows.forEach(function (row) {
        var checkBox = row.getElementsByTagName("td")[1].getElementsByTagName("input")[0];
        var id = checkBox.value;

        if (checkBox.checked) {
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

function parsePhoneFromModal() {
    var inputs = document.getElementById('phoneModal').getElementsByTagName("input");
    var phone = {};
    phone.hidden = inputs[0].value;
    phone.id = inputs[1].value;
    phone.countryCode = inputs[2].value;
    phone.number = inputs[3].value;
    if (inputs[4].checked) {
        phone.type = inputs[4].value;
    } else if (inputs[5].checked) {
        phone.type = inputs[5].value;
    } else{
        phone.type = "";
    }
    phone.comment = inputs[6].value;

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
    var tr = document.createElement("tr");
    tr.setAttribute("id", "phone" + phone.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td><input type='checkbox' name='selected' value=" + phone.id + "></td>" +
        "<td>" + phone.number + "</td>" +
        "<td>" + phone.type + "</td>" +
        "<td>" + phone.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='editPhone(this)'></td>" +
        "<td><input type='button' value='Удалить' onclick='deletePhone(this)'></td>";
    document.getElementById("phonesTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function phoneToDto(phone) {
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

function openPhoneModal() {
    var modal = document.getElementById('phoneModal');
    modal.style.display = "block";
}

function closePhoneModal() {
    var modal = document.getElementById('phoneModal');
    clearPhoneModal();
    modal.style.display = "none";
}