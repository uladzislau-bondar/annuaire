// todo delete input field for updated and existed
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

function editAttachment(element) {
    var id = element.parentNode.parentNode.id.substring(10);
    var attachment = parseAttachmentFromWindow(id);

    document.getElementById("attachmentHidden").value = attachment.hidden;
    document.getElementById("attachmentId").value = attachment.id;
    document.getElementById("attachmentName").value = attachment.name;
    document.getElementById("attachmentDateOfUpload").value = attachment.dateOfUpload;
    document.getElementById("attachmentComment").value = attachment.comment;
    document.getElementById("attachmentFileName").value = attachment.fileName;
    showFileInput(id);

    var modal = document.getElementById('attachmentModal');
    modal.style.display = "block";
}

function saveAttachment() {
    if (validateAttachmentModal()){
        var id = document.getElementById('attachmentId').value;
        if (id != '') {
            updateAttachment(id);
        } else {
            createNewAttachment();
        }

        closeAttachmentModal();
        showAttachmentTable();
    }
}


function clearAttachmentModal() {
    document.getElementById("attachmentHidden").value = "";
    document.getElementById("attachmentId").value = "";
    document.getElementById("attachmentName").value = "";
    document.getElementById("attachmentDateOfUpload").value = "";
    document.getElementById("attachmentComment").value = "";
    document.getElementById("attachmentFileName").value = "";

    deleteUndefinedAttachments();
}

function createNewAttachment() {
    var attachment = parseAttachmentFromModal();
    attachment.id = generateId();
    attachment.fileName = getFilePath(document.getElementsByName("file")[0]);
    attachment.dateOfUpload = today();
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

    var file = document.getElementsByName("file")[0];
    if (file == null){
        file = getExistedFile(id)
    }
    attachment.fileName = getFilePath(file);

    var attachmentRows = document.getElementById("attachment" + id).children;
    attachmentRows[0].getElementsByTagName("input")[0].value = attachment.hidden;
    attachmentRows[1].getElementsByTagName("input")[0].value = attachment.id;
    attachmentRows[2].getElementsByTagName("a")[0].innerHTML = attachment.name;
    attachmentRows[3].innerHTML = attachment.dateOfUpload;
    attachmentRows[4].innerHTML = attachment.comment;
    attachmentRows[5].getElementsByTagName("input")[0].value = attachment.fileName;

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
    hideAttachmentTableIfEmpty();
}

function deleteSelectedAttachments() {
    var rows = Array.prototype.slice.call(document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    rows.forEach(function (row) {
        var checkBox = row.getElementsByTagName("td")[1].getElementsByTagName("input")[0];
        var id = checkBox.value;

        if (checkBox.checked) {
            deleteAttachmentById(id);
        }
    });

    hideAttachmentTableIfEmpty();
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
    var attachment = {};
    attachment.hidden = document.getElementById("attachmentHidden").value;
    attachment.id = document.getElementById("attachmentId").value;
    attachment.name = document.getElementById("attachmentName").value;
    attachment.dateOfUpload = document.getElementById("attachmentDateOfUpload").value;
    attachment.comment = document.getElementById("attachmentComment").value;
    attachment.fileName = document.getElementById("attachmentFileName").value;

    return attachment;
}

function parseAttachmentFromWindow(id) {
    var attachment = {};
    var attachmentRows = document.getElementById("attachment" + id).children;
    attachment.hidden = attachmentRows[0].getElementsByTagName("input")[0].value;
    attachment.id = id;
    attachment.name = attachmentRows[2].children[0].innerHTML;
    // todo reads <a href instead of value
    if (attachment.name == undefined){
        attachment.name = attachmentRows[2].children[0].children[0].innerHTML;
    }
    attachment.dateOfUpload = attachmentRows[3].innerHTML;
    attachment.comment = attachmentRows[4].innerHTML;
    attachment.fileName = attachmentRows[5].getElementsByTagName("input")[0].value;

    return attachment;
}


function appendAddedAttachmentRow(attachment) {
    var tr = document.createElement("tr");
    tr.setAttribute("id", "attachment" + attachment.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td class='text-left'><input type='checkbox' name='selected' value=" + attachment.id + "></td>" +
        "<td class='text-left'>" + attachment.name + "</td>" +
        "<td class='text-left'>" + attachment.dateOfUpload + "</td>" +
        "<td class='text-left'>" + attachment.comment + "</td>" +
        "<td><input type='hidden' name='fileName' value='" + attachment.fileName + "'></td>" +
        "<td class='text-left'><input type='button' value='Изменить' onclick='editAttachment(this)'></td>" +
        "<td class='text-left'><input type='button' value='Удалить' onclick='deleteAttachment(this)'></td>";
    document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function generateId() {
    return (new Date()).getTime();
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

function getFilePath(input){
    return input.value.replace(/^.*[\\\/]/, '');
}

function today() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd='0'+dd
    }

    if(mm<10) {
        mm='0'+mm
    }

    today = yyyy+'-'+mm+'-'+dd;

    return today;
}

function hideAttachmentTableIfEmpty() {
    var table = document.getElementById("attachmentsTable");
    if (table.getElementsByTagName("tbody")[0].children.length == 0){
        table.style.display = "none";

        var label = document.getElementById("attachmentTableLabel");
        label.innerHTML = "Присоединения еще не созданы"
    }
}

function showAttachmentTable() {
    var table = document.getElementById("attachmentsTable");
    table.style.display = "block";

    var label = document.getElementById("attachmentTableLabel");
    label.innerHTML = "Список присоединений"
}