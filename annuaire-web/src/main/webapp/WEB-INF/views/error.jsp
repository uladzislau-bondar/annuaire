<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title><c:out value="${title}"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
</head>
<body>
<!-- todo create beatiful page -->
<div class="page-container page-container-responsive">
    <div class="row space-top-8 space-8 row-table">
        <div class="col-5 col-middle">
            <h1 class="text-jumbo text-ginormous">Oops!</h1>
            <h2>We can't seem to find the page you're looking for.</h2>
            <h6>Error code: <c:out value="${statusCode}" /> </h6>
        </div>
        <div class="col-5 col-middle text-center">
            <img src="https://a0.muscache.com/airbnb/static/error_pages/404-Airbnb_final-d652ff855b1335dd3eedc3baa8dc8b69.gif" width="313" height="428" alt="Girl has dropped her ice cream.">
        </div>
    </div>
</div>
</div>

</body>
</html>
