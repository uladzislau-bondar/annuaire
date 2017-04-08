function validateSearch() {
    if (validateAllInputsEmptiness(document)) {
        // todo create good-looking alert window
        alert("Заполните хотя бы одно поле!");
        return false;
    }
}

function someSelected() {
    var checkboxes = document.getElementsByName("selected");

    var result = false;
    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked){
            result = true;
        }
    });

    return result;
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
    if (name.trim() == "") {
        alert("Заполните имя присоединения!");
        return false;
    }

    var type = document.getElementById("attachmentHidden").value;
    if  (type == 'added' || type == '' || type == null){
        var id = document.getElementById("attachmentId").value;
        var file = document.getElementsByName("file")[0];
        if (file == null){
            file = getExistedFile(id);
        }
        var file_count = file.files.length;
        if (file_count === 0) {
            alert("Выберите файл!");
            return false;
        }
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
