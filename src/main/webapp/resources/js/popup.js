function showPhoneCreationForm() {
    var popup = window.open("/phone", "New Phone", "width=200, height=100");
    popup.focus();
}

function createNewPhone() {
    if (window.opener != null && !window.opener.closed) {
        var test = window.opener.document.getElementById("test");
        test.value = document.getElementById("test").value;
    }
    window.close();
}
