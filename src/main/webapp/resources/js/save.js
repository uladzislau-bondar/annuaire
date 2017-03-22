function save(path) {
    var contact = parseContact();
    var address = parseAddress();
    var phones = parsePhones();
    post(path, contact, address, phones);
}

function post(path, contact, address, phones) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    appendObjectToForm(form, contact);
    appendObjectToForm(form, address);
    appendArrayToForm(form, phones);

    document.body.appendChild(form);
    form.submit();
}

function appendArrayToForm(form, array) {
    array.forEach(function (item) {
        appendObjectToForm(form, item);
    });
}

function appendObjectToForm(form, item) {
    for (var key in item) {
        if (item.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", item[key]);

            form.appendChild(hiddenField);
        }
    }
}

function parsePhones() {
    var phones = [];

    var rows = document.getElementsByName("addedRow");

    rows.forEach(function (row) {
        phones.push(parseAddedPhone(row));
    });

    return phones;
}

function parseAddedPhone(row) {
    var phone = {};
    phone.countryCode = row.children[1].innerHTML;
    phone.number = row.children[2].innerHTML;
    phone.type = row.children[3].innerHTML;
    phone.comment = row.children[4].innerHTML;

    return phone;
}

function parseContact() {
    var contact = {};
    contact.firstName = document.getElementsByName("firstName")[0].value;
    contact.lastName = document.getElementsByName("lastName")[0].value;
    contact.middleName = document.getElementsByName("middleName")[0].value;
    contact.dateOfBirth = document.getElementsByName("dateOfBirth")[0].value;
    contact.sex = document.getElementsByName("sex")[0].value;
    contact.citizenship = document.getElementsByName("citizenship")[0].value;
    contact.maritalStatus = document.getElementsByName("maritalStatus")[0].value;
    contact.website = document.getElementsByName("website")[0].value;
    contact.email = document.getElementsByName("email")[0].value;
    contact.placeOfWork = document.getElementsByName("placeOfWork")[0].value;

    return contact;
}

function parseAddress() {
    var address = {};
    address.country = document.getElementsByName("country")[0].value;
    address.city = document.getElementsByName("city")[0].value;
    address.address = document.getElementsByName("address")[0].value;
    address.zip = document.getElementsByName("zip")[0].value;

    return address;
}
