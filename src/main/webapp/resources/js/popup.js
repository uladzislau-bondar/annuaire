var phones = [];

function showPhoneCreationForm() {
    var popup = window.open("/phone", "Create new phone", "width=800, height=800");
    popup.focus();
}

function createNewPhone() {
    if (window.opener != null) {
        var phone = fillPhoneFromPopup();

        var inputs = window.opener.document.getElementById("additionalRows").getElementsByTagName("input");
        var inputList = Array.prototype.slice.call(inputs);
        inputList.forEach(function (item) {
            //todo other fields
            if (item.name == "countryCode") {
                item.value = phone.countryCode;
            }
        });
    }
    window.close();
}

function fillPhoneFromPopup() {
    var phone = {};
    phone.countryCode = document.getElementsByName("countryCode")[0].value;
    phone.number = document.getElementsByName("number")[0].value;
    phone.type = document.getElementsByName("type")[0].value;
    phone.comment = document.getElementsByName("comment")[0].value;

    return phone;
}

function appendNewRow(phone) {
    var tr = document.createElement("tr");
    tr.innerHTML = fillRowWithElementsFromPhone(phone);
}

function fillRowWithElementsFromPhone(phone) {
    .// todo
}
