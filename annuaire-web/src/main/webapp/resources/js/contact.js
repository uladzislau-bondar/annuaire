document.addEventListener("DOMContentLoaded", ready);

function ready() {
    var alertClick = document.getElementById("alertClick");
    if (alertClick != null){
        alertClick.click();
    }

    hidePhoneTableIfEmpty();
    hideAttachmentTableIfEmpty();
}