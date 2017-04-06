//todo close all modals

window.onclick = function (event) {
    var modal = document.getElementsByClassName('phoneModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
    modal = document.getElementsByClassName('attachmentModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

document.addEventListener("DOMContentLoaded", ready);

function ready() {
    hidePhoneTableIfEmpty();
    hideAttachmentTableIfEmpty();
}