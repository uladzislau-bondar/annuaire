function changeTemplate(index) {
    var textarea = document.getElementById("message");
    if (index == 0){
        textarea.value = "";
    } else if (index == 1){
        textarea.value = document.getElementsByName("birthday")[0].value;
    } else if (index == 2){
        textarea.value = document.getElementsByName("christmas")[0].value;
    }
}
