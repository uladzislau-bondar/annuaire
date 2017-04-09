document.addEventListener("DOMContentLoaded", ready);

function ready() {
    var alertClick = document.getElementById("alertClick");
    if (alertClick != null){
        alertClick.click();
    }
}

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

function paginate(url, method) {
    if (method == 'show'){
        var a = document.createElement("a");
        a.href = url;
        a.click();
    }
    else if (method == 'search'){
        var form = document.createElement("form");
        form.action = url;
        form.method = 'post';

        var params = document.getElementById("searchParams").value;
        params = params.replace(/'/g, "\"");
        var paramsObject = JSON.parse(params);
        appendObjectToForm(form, paramsObject);
        form.submit();
    }
}