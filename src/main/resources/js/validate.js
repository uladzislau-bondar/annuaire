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
