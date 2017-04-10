function validateSearch() {
    if (validateAllInputsEmptiness(document)) {
        showAlert("Заполните хотя бы одно поле!");
        return false;
    }

    if (validateDate()){

    }
}

function someSelected() {
    var checkboxes = document.getElementsByName("selected");

    var result = false;
    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
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
        showAlert("Заполните поля телефона!");
        return false;
    }

    return true;
}

function validateAttachmentModal() {
    var name = document.getElementById("attachmentName").value;
    if (name.trim() == "") {
        showAlert("Заполните имя присоединения!");
        return false;
    }

    var type = document.getElementById("attachmentHidden").value;
    if (type == 'added' || type == '' || type == null) {
        var id = document.getElementById("attachmentId").value;
        var file = document.getElementsByName("file")[0];
        if (file == null) {
            file = getExistedFile(id);
        }
        var file_count = file.files.length;
        if (file_count === 0) {
            showAlert("Выберите файл!");
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

function validateContact() {
    return validateDate();
}

function validateDate() {
    var date = document.getElementById("dateOfBirth").value;
    if (!isValidDate(date)) {
        showAlert("Неправильная дата!");
        return false;
    }

    return true;
}

function isValidDate(date) {
    if (date == null || date.trim() == ''){
        return true;
    } else{
        var matches = /(\d{4})-(\d{2})-(\d{2})/.exec(date);
        if (matches == null) {
            return false;
        }
        var day = matches[3];
        var month = matches[2] - 1;
        var year = matches[1];
        var composedDate = new Date(year, month, day);

        if (!isInPast(composedDate)){
            return false;
        }

        return composedDate.getDate() == day &&
            composedDate.getMonth() == month &&
            composedDate.getFullYear() == year;
    }
}

function isInPast(date) {
    var now = new Date();
    return date <= now;
}
