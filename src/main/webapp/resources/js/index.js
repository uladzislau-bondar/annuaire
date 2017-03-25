function processSelected(input) {
    var form = document.getElementById("contactsForm");
    var url = form.action;

    if (input.id == 'deleteButton'){
        form.action = url + "?method=delete";
    } else if (input.id == 'emailButton'){
        form.action = url + "?method=email";
    }

    form.submit();
}