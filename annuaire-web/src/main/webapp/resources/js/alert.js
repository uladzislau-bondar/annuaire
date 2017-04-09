function showAlert(message) {
    var alert = document.getElementById("alert");
    document.getElementById("alertMessage").innerHTML = message;
    alert.style.opacity = "1";
    alert.style.display = 'block';

    setTimeout(function () {
        hideAlert();
    }, 3000);
}

function hideAlert() {
    var alert = document.getElementById("alert");
    alert.style.opacity = "0";

    setTimeout(function () {
        alert.style.display = 'none';
    }, 600);
}