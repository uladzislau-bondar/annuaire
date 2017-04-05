function requireFirstName(){
    var element, msg;
    element = document.getElementsByName("firstName")[0];

    if (element.value.length > 32) {
        msg = "Имя должно быть короче 32 символов.";
    }
    element.innerHTML = msg;
}

function validateFields() {
    requireFirstName();
}

function validateSearch() {
    if (validateAllInputsEmptiness()){
        // todo create good-looking alert window
        alert("Заполните хотя бы одно поле!");
        return false;
    }
}

function validateAllInputsEmptiness() {
    var allEmpty = true;

    var inputs = Array.prototype.slice.call(document.getElementsByTagName("input"));
    var textInputs = inputs.filter(function (input) {
        return input.type != "radio";
    });

    textInputs.forEach(function (input) {
        if (input.value != ''){
            allEmpty = false;
        }
    });

    return allEmpty;
}
