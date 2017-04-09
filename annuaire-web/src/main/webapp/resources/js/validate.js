function validateSearch() {
    if (validateAllInputsEmptiness(document)) {
        showAlert("Заполните хотя бы одно поле!");
        return false;
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
    var date = document.getElementById("dateOfBirth").value;
    if (!validateDate(date)){
        showAlert("Неправильная дата!");
    }

    return true;
}

// todo fix date validation
function validateDate(date) {
    var matches = /^(\d{4}-\d{1,2}-\d{1,2})$/.exec(date);
    if (matches == null) return false;
    var d = matches[2];
    var m = matches[1] - 1;
    var y = matches[3];
    var composedDate = new Date(y, m, d);
    return composedDate.getDate() == d &&
        composedDate.getMonth() == m &&
        composedDate.getFullYear() == y;
}
