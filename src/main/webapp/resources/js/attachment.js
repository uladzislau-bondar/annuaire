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
    showFileInput(id);

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

    clearAttachmentModal();
    modal.style.display = "none";
}

function clearAttachmentModal() {
    var inputs = document.getElementById('attachmentModal').getElementsByTagName("input");
    inputs[0].value = "";
    inputs[1].value = "";
    inputs[2].value = "";
    inputs[3].value = "";
    inputs[4].value = "";

    deleteUndefinedAttachments();
}

function createNewAttachment() {
    var attachment = parseAttachmentFromModal();
    attachment.id = generateId();
    //todo add date
    saveFile(attachment.id);
    appendAddedAttachmentRow(attachment);
}


function saveFile(id) {
    var file = document.getElementsByName("file")[0];
    file.setAttribute("id", "file" + id);
    file.setAttribute("name", "addedAttachment");
    file.style.display = "none";
}

function showFileInput(id) {
    var input = document.getElementById("file" + id);
    if (input == null){
        input = fileInput();
        input.setAttribute("id", "file" + id);
        insertAfter(fileInput(), document.getElementById("attachmentComment"));
    } else{
        input.style.display = "block";
    }
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
    updateFile(attachment.id);
}

function updateFile(id) {
    var file = document.getElementsByName("file")[0];
    if (file == null){
        file = getExistedFile(id)
    }

    file.setAttribute("id", "file" + id);
    file.setAttribute("name", "updatedAttachment");
    file.style.display = "none";
}

function getExistedFile(id) {
    return document.getElementById("file" + id);
}

function deleteAttachmentById(id) {
    var type = document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value;

    if (type == 'added') {
        deleteAttachmentRow(id);
    } else {
        deleteExistedAttachment(id);
    }

    deleteFile(id);
}

function deleteFile(id) {
    var file = document.getElementById("file" + id);
    if (file != null){
        file.remove();
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
    insertAfter(fileInput(), document.getElementById("attachmentComment"));
    modal.style.display = "block";
}

function closeAttachmentModal() {
    var modal = document.getElementById('attachmentModal');
    clearAttachmentModal();
    modal.style.display = "none";
}

function fileInput() {
    var input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("name", "file");

    return input;
}

function deleteUndefinedAttachments() {
    var undefinedAttachments = Array.prototype.slice.call(document.getElementsByName("file"));
    undefinedAttachments.forEach(function (attachment) {
        attachment.remove();
    });
}

function insertAfter(newNode, referenceNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}