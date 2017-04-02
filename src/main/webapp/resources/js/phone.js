var phoneModal = {
    hidden: document.getElementById("phoneHidden"),
    id: document.getElementById("phoneId"),
    countryCode: document.getElementById("phoneCountryCode"),
    number: document.getElementById("phoneNumber"),
    homeRadio: document.getElementById("phoneHomeRadio"),
    mobileRadio: document.getElementById("mobileRadio"),
    comment: document.getElementById("phoneComment")
};

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

    phoneModal.hidden.value = phone.hidden;
    phoneModal.id.value = phone.id;
    phoneModal.countryCode.value = phone.countryCode;
    phoneModal.number.value = phone.number;
    if (phone.type == phoneModal.homeRadio.value){
        phoneModal.homeRadio.setAttribute("checked", "checked");
    } else if (phone.type == phoneModal.mobileRadio.value){
        phoneModal.mobileRadio.setAttribute("checked", "checked");
    }
    phoneModal.comment.value = phone.comment;

    openPhoneModal();
}

function savePhone() {
    var id = phoneModal.id.value;
    if (id != '') {
        updatePhone(id);
    } else {
        createNewPhone();
    }

    closePhoneModal();
}

function createNewPhone() {
    var phone = parsePhoneFromModal();
    var phoneDto = phoneToDto(phone);

    phoneDto.id = generateId();

    appendAddedPhoneRow(phoneDto);
}

function clearPhoneModal() {
    phoneModal.hidden.value = "";
    phoneModal.id.value = "";
    phoneModal.countryCode.value = "";
    phoneModal.number.value = "";
    phoneModal.homeRadio.removeAttribute("checked");
    phoneModal.mobileRadio.removeAttribute("checked");
    phoneModal.comment.value = "";
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
    var phone = {};
    phone.hidden = phoneModal.hidden.value;
    phone.id = phoneModal.id.value;
    phone.countryCode = phoneModal.countryCode.value;
    phone.number = phoneModal.number.value;
    if (phoneModal.homeRadio.checked) {
        phone.type = phoneModal.homeRadio.value;
    } else if (phoneModal.mobileRadio.checked) {
        phone.type = phoneModal.mobileRadio.value;
    } else{
        phone.type = "";
    }
    phone.comment = phoneModal.comment.value;

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