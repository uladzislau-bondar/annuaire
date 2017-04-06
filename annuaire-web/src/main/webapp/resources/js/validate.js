function validateSearch() {
    if (validateAllInputsEmptiness(document)){
        // todo create good-looking alert window
        alert("Заполните хотя бы одно поле!");
        return false;
    }
}

// todo
function validatePhoneModal() {
    var modal = document.getElementById('phoneModal');
    if (validateAllInputsEmptiness(modal)){
        // todo create good-looking alert window
        alert("Заполните нужные поля!");
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
        if (input.value != ''){
            allEmpty = false;
        }
    });

    return allEmpty;
}
