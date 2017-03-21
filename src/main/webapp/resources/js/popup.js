var phones = [];

function showPhoneCreationForm() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function createNewPhone() {
    if (window.opener != null) {
        var phone = {};
        phone.countryCode = document.getElementById("countryCode").value;

        var inputs = window.opener.document.getElementById("additionalRows").getElementsByTagName("input");
        var inputList = Array.prototype.slice.call(inputs);
        inputList.forEach(function (item, i, array) {
            if (item.name == "countryCode") {
                item.value = phone.countryCode;
            }
        });
    }
    window.close();
}
