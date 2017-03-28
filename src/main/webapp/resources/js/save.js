function save() {
    var form = document.getElementById("form");

    var phoneDtos = parsePhones();
    var phones = [];
    phoneDtos.forEach(function (item) {
        phones.push(dtoToPhone(item));
    });
    var addedPhones = phones.filter(function (phone) {
        return phone.hidden == 'added';
    });
    var updatedPhones = phones.filter(function (phone) {
        return phone.hidden == 'updated';
    });

    var phonesObject = {};
    phonesObject.phonesToAdd = JSON.stringify(addedPhones);
    phonesObject.phonesToUpdate = JSON.stringify(updatedPhones);

    var deletedPhones = parseDeletedPhones();
    deletedPhones.forEach(function (item) {
        form.appendChild(item);
    });

    var deletedAttachments = parseDeletedAttachments();
    deletedAttachments.forEach(function (item) {
        form.appendChild(item);
    });

    appendObjectToForm(form, phonesObject);
    appendArrayToForm(form, deletedPhones);
    appendArrayToForm(form, deletedAttachments);

    form.submit();
}

function appendArrayToForm(form, array) {
    array.forEach(function (item) {
        appendObjectToForm(form, item);
    });
}

function appendObjectToForm(form, obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", obj[key]);

            form.appendChild(hiddenField);
        }
    }
}


function parsePhones() {
    var phones = [];

    var rows = Array.prototype.slice
        .call(document.getElementById("phonesTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));

    rows.forEach(function (row) {
        phones.push(parsePhone(row));
    });

    return phones;
}

function parsePhone(row) {
    var phone = {};
    phone.hidden = row.children[0].getElementsByTagName("input")[0].value;
    phone.id = row.children[1].getElementsByTagName("input")[0].value;
    phone.number = row.children[2].innerHTML;
    phone.type = row.children[3].innerHTML;
    phone.comment = row.children[4].innerHTML;

    return phone;
}

function parseDeletedPhones() {
    return Array.prototype.slice.call(document.getElementsByName("phoneToDelete"));
}

function parseDeletedAttachments()
{
    return Array.prototype.slice.call(document.getElementsByName("attachmentToDelete"));
}
