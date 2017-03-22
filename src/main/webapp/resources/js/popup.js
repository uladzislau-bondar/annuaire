function showPhoneCreationForm() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function createNewPhone() {
    if (window.opener != null) {
        var phone = fillPhoneFromPopup();
        appendNewRow(phone);
    }
    window.close();
}

function fillPhoneFromPopup() {
    var phone = {};
    phone.id = 1;
    phone.countryCode = document.getElementsByName("countryCode")[0].value;
    phone.number = document.getElementsByName("number")[0].value;
    phone.type = document.getElementsByName("type")[0].value;
    phone.comment = document.getElementsByName("comment")[0].value;

    return phone;
}

function fillPhoneFromTableRow(row) {
    var phone = {};
    phone.number = row.children[1].innerHTML;
    phone.type = row.children[2].innerHTML;
    phone.comment = row.children[3].innerHTML;

    return phone;
}

//todo refactor
//todo process deleting and editing
function appendNewRow(phone) {
    var tr = window.opener.document.createElement("tr");
    tr.setAttribute("name", "addedRow");
    tr.innerHTML = "<td><input type='checkbox' name='selected' id='phone" + phone.id + "'></td>" +
        "<td>" + "+" + phone.countryCode + "-" + phone.number + "</td>" +
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

function save(path) {
    alert(path);

    var phones = parsePhones();
    post(path, phones);
}

function post(path, params) {
    var method = "post";

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            alert(key);

            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

function parsePhones() {
    var phones = [];

    var rows = document.getElementsByName("addedRow");

    alert(rows[0].innerHTML);

    rows.forEach(function (row) {
        phones.push(fillPhoneFromTableRow(row));
    });
}
// function fillRowWithElementsFromPhone(row, phone) {
//     row.appendNewCell()
// }
//
// function appendNewCell(content) {
//     var td = document.createElement("td");
//     td.innerHTML = content;
//
//     return td;
// }
