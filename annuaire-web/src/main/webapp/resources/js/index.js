function processSelected(input) {
    var form = document.getElementById("contactsForm");
    var url = form.action;

    if (input.id == 'deleteButton') {
        if (someSelected()) {
            form.action = url + "?method=delete";
        }
        else {
            return false;
        }
    } else if (input.id == 'emailButton') {
        form.action = url + "?method=email";
    }

    form.submit();
}