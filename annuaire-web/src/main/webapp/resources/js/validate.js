function validateSearch() {
    if (validateAllInputsEmptiness(document)) {
        // todo create good-looking alert window
        alert("Заполните хотя бы одно поле!");
        return false;
    }
}

function validatePhoneModal() {
    var countryCode = document.getElementById("phoneCountryCode").value;
    var operatorCode = document.getElementById("phoneOperatorCode").value;
    var number = document.getElementById("phoneNumber").value;

    if (countryCode == "" || operatorCode == "" || number == "") {
        alert("Заполните поля телефона!");
        return false;
    }

    return true;
}

function validateAttachmentModal() {
    var name = document.getElementById("attachmentName").value;
    var file_count = document.getElementsByName("file")[0].files.length;

    if (name.trim() == "") {
        alert("Заполните имя присоединения!");
        return false;
    }
    if (file_count === 0) {
        alert("Выберите файл!");
        return false;
    }

    return true;
}

function validateAllInputsEmptiness(element) {
    var allEmpty = true;

    var inputs = Array.prototype.slice.call(element.getElementsByTagName("input"));
    var textInputs = inputs.filter(function (input) {
        return input.type != "radio" && input.type != "checkbox";
    });

    textInputs.forEach(function (input) {
        if (input.value != '') {
            allEmpty = false;
        }
    });

    return allEmpty;
}
