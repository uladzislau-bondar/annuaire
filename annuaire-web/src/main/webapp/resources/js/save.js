function save() {
    if (validateContact()){
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

        var attachments = parseAttachments();
        var addedAttachments = attachments.filter(function (attachment) {
            return attachment.hidden == 'added'
        });
        var updatedAttachments = attachments.filter(function (attachment) {
            return attachment.hidden == 'updated';
        });

        var attachmentsObject = {};
        attachmentsObject.attachmentsToAdd = JSON.stringify(addedAttachments);
        attachmentsObject.attachmentsToUpdate = JSON.stringify(updatedAttachments);

        var deletedPhones = parseDeletedPhones();
        deletedPhones.forEach(function (item) {
            form.appendChild(item);
        });

        var deletedAttachments = parseDeletedAttachments();
        deletedAttachments.forEach(function (item) {
            form.appendChild(item);
        });

        appendObjectToForm(form, phonesObject);
        appendObjectToForm(form, attachmentsObject);
        appendArrayToForm(form, deletedPhones);
        appendArrayToForm(form, deletedAttachments);

        form.submit();
    }


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

function parseAttachments() {
    var attachments = [];

    var rows = Array.prototype.slice
        .call(document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));

    rows.forEach(function (row) {
        attachments.push(parseAttachment(row));
    });

    return attachments;
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

function parseAttachment(row) {
    var attachment = {};
    attachment.hidden = row.children[0].getElementsByTagName("input")[0].value;
    attachment.id = row.children[1].getElementsByTagName("input")[0].value;
    if (attachment.hidden === 'added'){
        attachment.name = row.children[2].innerHTML;
    } else {
        attachment.name = row.children[2].children[0].innerHTML;
    }
    attachment.dateOfUpload = row.children[3].innerHTML;
    attachment.comment = row.children[4].innerHTML;
    attachment.fileName = row.children[5].getElementsByTagName("input")[0].value;

    return attachment;
}

function parseDeletedPhones() {
    return Array.prototype.slice.call(document.getElementsByName("phoneToDelete"));
}

function parseDeletedAttachments()
{
    return Array.prototype.slice.call(document.getElementsByName("attachmentToDelete"));
}
