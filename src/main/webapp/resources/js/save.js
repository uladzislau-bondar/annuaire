function save(path) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    var contact = parseContact();
    var address = parseAddress();

    var phones = parsePhones();
    var addedPhones = phones.filter(function (phone) {
        return phone.hidden == 'added';
    });
    var addedPhonesObject = {};
    addedPhonesObject.phonesToAdd = JSON.stringify(addedPhones);

    var updatedPhones = phones.filter(function (phone) {
        return phone.hidden == 'updated';
    });
    var updatedPhonesObject = {};
    updatedPhonesObject.phonesToUpdate = JSON.stringify(updatedPhones);

    var deletedPhones = parseDeletedPhones();
    deletedPhones.forEach(function (item) {
        form.appendChild(item);
    });

    appendObjectToForm(form, contact);
    appendObjectToForm(form, address);
    appendObjectToForm(form, addedPhonesObject);
    appendObjectToForm(form, updatedPhonesObject);
    appendArrayToForm(form, deletedPhones);

    document.body.appendChild(form);
    form.submit();
}

function post(path) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    var contact = parseContact();
    var address = parseAddress();

    var phones = parsePhones();
    var addedPhones = phones.filter(function (phone) {
        return phone.hidden == 'added';
    });
    var addedPhonesObject = {};
    addedPhonesObject.phonesToAdd = JSON.stringify(addedPhones);
    alert(addedPhonesObject.phonesToAdd);

    var updatedPhones = phones.filter(function (phone) {
        return phone.hidden == 'updated';
    });
    var updatedPhonesObject = {};
    updatedPhonesObject.phonesToUpdate = JSON.stringify(updatedPhones);
    alert(updatedPhonesObject.phonesToAdd);

    var deletedPhones = parseDeletedPhones();
    deletedPhones.forEach(function (item) {
        alert(item.value);
        document.body.appendChild(item);
    });

    appendObjectToForm(form, contact);
    appendObjectToForm(form, address);
    appendObjectToForm(form, addedPhonesObject);
    appendObjectToForm(form, updatedPhonesObject);
    appendArrayToForm(form, deletedPhones);

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

    var tableRows = Array.prototype.slice
        .call(document.getElementById("phonesTable").getElementsByTagName("tbody")[0].getElementsByTagName("tr"));
    var rows = tableRows.slice(1);

    rows.forEach(function (row) {
        phones.push(parsePhone(row));
    });

    return phones;
}

function parsePhone(row) {
    var phone = {};
    phone.hidden = row.children[0].getElementsByTagName("input")[0].value;
    phone.id = row.children[1].getElementsByTagName("input")[0].value;
    phone.countryCode = row.children[2].innerHTML;
    phone.number = row.children[3].innerHTML;
    phone.type = row.children[4].innerHTML;
    phone.comment = row.children[5].innerHTML;

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

function parseDeletedPhones() {
    return Array.prototype.slice.call(document.getElementsByName("phoneToDelete"));
}
