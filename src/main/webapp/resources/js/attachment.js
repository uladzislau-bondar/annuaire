function editAttachment(element) {
    var id = element.parentNode.parentNode.id.substring(10);
    var attachment = parseAttachmentFromWindow(id);

    var modal = document.getElementById('attachmentModal');
    var inputs = modal.getElementsByTagName("input");

    inputs[0].value = attachment.hidden;
    inputs[1].value = attachment.id;
    inputs[2].value = attachment.name;
    inputs[3].value = attachment.dateOfUpload;
    inputs[4].value = attachment.comment;

    modal.style.display = "block";
}

function saveAttachment() {
    var modal = document.getElementById('attachmentModal');
    if (modal.getElementsByTagName("input")[1].value != '') {
        var id = modal.getElementsByTagName("input")[1].value;

        updateAttachment(id);
    } else {
        createNewAttachment();
    }

    clearModal();
    modal.style.display = "none";
}

function clearModal() {
    var inputs = document.getElementById('attachmentModal').getElementsByTagName("input");
    inputs[0].value = "";
    inputs[1].value = "";
    inputs[2].value = "";
    inputs[3].value = "";
    inputs[4].value = "";
}

function createNewAttachment() {
    var attachment = parseAttachmentFromModal();
    attachment.id = generateId();
    //todo add date
    appendAddedAttachmentRow(attachment);
}

//todo change
function pickFile() {
    var id = document.getElementsByName("id")[0].value;
    var fileInput = window.opener.document.getElementById("attachment" + id).children[5].getElementsByTagName("input")[0];
    alert(fileInput.name);
    fileInput.click();
}

function updateAttachment(id) {
    var attachment = parseAttachmentFromModal();
    if (attachment.hidden == 'existed') {
        attachment.hidden = 'updated';
    }
    document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value =
        attachment.hidden;
    document.getElementById("attachment" + id).children[1].getElementsByTagName("input")[0].value =
        attachment.id;
    document.getElementById("attachment" + id).children[2].innerHTML = attachment.name;
    document.getElementById("attachment" + id).children[3].innerHTML = attachment.dateOfUpload;
    document.getElementById("attachment" + id).children[4].innerHTML = attachment.comment;
}

function deleteAttachmentById(id) {
    var type = document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value;

    if (type == 'added') {
        deleteAttachmentRow(id);
    } else {
        deleteExistedAttachment(id);
    }
}

function deleteAttachment(element) {
    var id = element.parentNode.parentNode.id.substring(10);
    deleteAttachmentById(id);
}

function deleteSelectedAttachments() {
    var rows = Array.prototype.slice.call(document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    rows.forEach(function (row) {
        var checkBox = row.getElementsByTagName("td")[1].getElementsByTagName("input")[0];
        var id = checkBox.value;

        if (checkBox.checked) {
            deleteAttachmentById(id);
        }
    })
}


function deleteAttachmentRow(id) {
    document.getElementById("attachment" + id).outerHTML = "";
}

function deleteExistedAttachment(id) {
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "attachmentToDelete");
    input.setAttribute("value", id);
    document.body.appendChild(input);

    deleteAttachmentRow(id);
}

function closePopup() {
    window.close();
}

function parseAttachmentFromModal() {
    var inputs = document.getElementById('attachmentModal').getElementsByTagName("input");
    var attachment = {};
    attachment.hidden = inputs[0].value;
    attachment.id = inputs[1].value;
    attachment.name = inputs[2].value;
    attachment.dateOfUpload = inputs[3].value;
    attachment.comment = inputs[4].value;

    return attachment;
}

function parseAttachmentFromWindow(id) {
    var attachment = {};
    attachment.hidden = document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value;
    attachment.id = id;
    attachment.name = document.getElementById("attachment" + id).children[2].innerHTML;
    attachment.dateOfUpload = document.getElementById("attachment" + id).children[3].innerHTML;
    attachment.comment = document.getElementById("attachment" + id).children[4].innerHTML;

    return attachment;
}


function appendAddedAttachmentRow(attachment) {
    var tr = document.createElement("tr");
    tr.setAttribute("id", "attachment" + attachment.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td><input type='checkbox' name='selected' value=" + attachment.id + "></td>" +
        "<td>" + attachment.name + "</td>" +
        "<td>" + attachment.dateOfUpload + "</td>" +
        "<td>" + attachment.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='editAttachment(this)'></td>" +
        "<td><input type='button' value='Удалить' onclick='deleteAttachment(this)'></td>";
    document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function generateId() {
    return (new Date()).getTime();
}

function openAttachmentModal() {
    var modal = document.getElementById('attachmentModal');
    modal.style.display = "block";
}

function closeAttachmentModal() {
    var modal = document.getElementById('attachmentModal');
    clearModal();
    modal.style.display = "none";
}