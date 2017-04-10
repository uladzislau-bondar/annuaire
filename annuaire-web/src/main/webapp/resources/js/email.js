function changeTemplate() {
    var textarea = document.getElementById("message");

    var template = document.getElementById("template");
    var templateName = template.options[template.selectedIndex].value;
    if (templateName == 'default'){
        textarea.value = '';
    } else {
        textarea.value = document.getElementById(templateName).value;
    }
}
