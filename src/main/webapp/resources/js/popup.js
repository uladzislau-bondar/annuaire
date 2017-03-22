function showPhoneCreationPopup() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function showPhoneEditingPopup(id) {
    var popup = window.open("/phone", "Edit phone", "width=800, height=800");
    popup.document.getElementsByName("countryCode")[0].value =
        window.document.getElementById("phone" + id).children[0].innerHTML;
    popup.focus();
}

function createNewPhone() {
    if (window.opener != null) {
        var phone = parsePhoneFromPopup();
        appendAddedPhoneRow(phone);
    }
    window.close();
}

function closePopup() {
    window.close();
}

function parsePhoneFromPopup() {
    var phone = {};
    phone.id = 1;
    phone.countryCode = document.getElementsByName("countryCode")[0].value;
    phone.number = document.getElementsByName("number")[0].value;
    phone.type = document.getElementsByName("type")[0].value;
    phone.comment = document.getElementsByName("comment")[0].value;

    return phone;
}


//todo refactor
//todo process deleting and editing
function appendAddedPhoneRow(phone) {
    var tr = window.opener.document.createElement("tr");
    tr.setAttribute("name", "addedRow");
    tr.innerHTML = "<td><input type='checkbox' name='selected' id='phone" + phone.id + "'></td>" +
        "<td>" + phone.countryCode + "</td>" +
        "<td>" + phone.number    + "</td>" +
        "<td>" + phone.type + "</td>" +
        "<td>" + phone.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='deleteRow()'></td>" +
        "<td><input type='button' value='Удалить' onclick='editRow()'></td>";
    window.opener.document.getElementById("phonesTable").appendChild(tr);
}

function deleteRow(id) {

}

function editRow(id) {

}
