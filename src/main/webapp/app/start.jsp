<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<button type="button" onclick="logout()">Log out</button>
<div>
    <button type="button" onclick="addEvent()">Add Event</button>
    <button type="button" onclick="myEvents()">My Events</button>
</div>

<script>
    function addEvent() {
        location.href = "/app/add-event.jsp"
    }

    function myEvents() {
        location.href = "/app/my-events.jsp"
    }


    function logout() {
        fetch("<c:url value='/api/auth/logout'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/";
            });
    }
</script>
</body>
</html>
