function selectPhoto() {
    document.getElementById('photoInput').click()
}

function changePhoto(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onloadend = function () {
            var photo = document.getElementById("photo");
            photo.src = reader.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}