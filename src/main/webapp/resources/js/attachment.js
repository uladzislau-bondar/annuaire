function showAttachmentCreationPopup() {
    var popup = window.open("/attachment", "Create new attachment", "width=800, height=800");
    popup.focus();
}

function editAttachment(element) {
    //todo refactor getting id
    var id = element.parentNode.parentNode.id.substring(10);
    var popup = window.open("/attachment", "Edit attachment", "width=800, height=800");
    popup.focus();
    popup.onload = function () {
        var attachment = parseAttachmentFromWindow(id);
        popup.document.getElementsByName("hidden")[0].value = attachment.hidden;
        popup.document.getElementsByName("id")[0].value = attachment.id;
        popup.document.getElementsByName("name")[0].value = attachment.name;
        popup.document.getElementsByName("dateOfUpload")[0].value = attachment.dateOfUpload;
        popup.document.getElementsByName("comment")[0].value = attachment.comment;
    }
}

function saveAttachment() {
    if (document.getElementsByName("id")[0].value != '') {
        var id = document.getElementsByName("id")[0].value;

        updateAttachment(id);
    } else {
        createNewAttachment();
    }
    window.close();
}

function createNewAttachment() {
    if (window.opener != null) {
        var attachment = parseAttachmentFromPopup();
        attachment.id = generateId();
        //todo add date
        appendAddedAttachmentRow(attachment);
    }
}

function updateAttachment(id) {
    if (window.opener != null) {
        var attachment = parseAttachmentFromPopup();
        if (attachment.hidden == 'existed'){
            attachment.hidden = 'updated';
        }
        window.opener.document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value =
            attachment.hidden;
        window.opener.document.getElementById("attachment" + id).children[1].getElementsByTagName("input")[0].value =
            attachment.id;
        window.opener.document.getElementById("attachment" + id).children[2].innerHTML = attachment.name;
        window.opener.document.getElementById("attachment" + id).children[3].innerHTML = attachment.dateOfUpload;
        window.opener.document.getElementById("attachment" + id).children[4].innerHTML = attachment.comment;
    }
}

function deleteAttachmentById(id) {
    var type = document.getElementById("attachment" + id).children[0].getElementsByTagName("input")[0].value;

    if (type == 'added'){
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

        if (checkBox.checked){
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

function parseAttachmentFromPopup() {
    var attachment = {};
    attachment.hidden = document.getElementsByName("hidden")[0].value;
    attachment.id = document.getElementsByName("id")[0].value;
    attachment.name = document.getElementsByName("name")[0].value;
    attachment.dateOfUpload = document.getElementsByName("dateOfUpload")[0].value;
    attachment.comment = document.getElementsByName("comment")[0].value;

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
    var tr = window.opener.document.createElement("tr");
    tr.setAttribute("id", "attachment" + attachment.id);
    tr.innerHTML = "<td><input type='hidden' value='added'></td>" +
        "<td><input type='checkbox' name='selected' value="+ attachment.id + "></td>" +
        "<td>" + attachment.name + "</td>" +
        "<td>" + attachment.dateOfUpload + "</td>" +
        "<td>" + attachment.comment + "</td>" +
        "<td><input type='button' value='Изменить' onclick='editAttachment(this)'></td>" +
        "<td><input type='button' value='Удалить' onclick='deleteAttachment(this)'></td>";
    window.opener.document.getElementById("attachmentsTable").getElementsByTagName("tbody")[0].appendChild(tr);
}

function generateId() {
    return (new Date()).getTime();
}