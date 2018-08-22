<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Events</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
</head>
<style>
    li {
        width: 500px;
    }

    #editButton {
        margin-left: 300px;
    }

    #createPoolButton {
        margin-left: 10px;
    }
</style>
<body onload="getEventsFromDB()">
<p>
    <button onclick="goToTheMainPage()">Go to the main page</button>
</p>
<ul class="w3-ul" id="myevent-list">
    <li w3-repeat="eventList" type="text" id="eventElement">
        {{eventName}}
        <p>
            <button onclick="goToEditPage()" id="editButton">Edit</button>
            <button onclick="goToCreatePoolPage()" id="createPoolButton">Create Pool</button>
        </p>
    </li>
</ul>

<script>
    function goToEditPage() {
        location.href = "/app/add-event.jsp"
    }

    function goToCreatePoolPage() {

    }

    function getEventsFromDB() {
        fetch("<c:url value='/api/event/getevents'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (events) {
            console.log(events);
            if (events.eventList.length > 0) {
                document.getElementById("myevent-list").classList.remove("w3-hide");
                w3DisplayData("myevent-list", events);
            }
        })
    }
</script>

</body>
</html>
