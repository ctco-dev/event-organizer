<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Events</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
</head>

<body onload="getEventsFromDB()">
<header id="header"><h1>My Events</h1></header>
<p>
    <button onclick="goToTheMainPage()">Go to the main page</button>
</p>
<ul class="w3-ul" id="myevent-list">
    <li w3-repeat="eventList" type="text" id="eventElement" class="w3-hide">
        <a href="<c:url value='/app/jsp/event.jsp'/>?id={{eventID}}">{{eventName}}</a>
        <p>
            <button onclick="goToEditPage('{{eventID}}')" id="goToEdit">Edit</button>
            <button onclick="deleteEvent('{{eventID}}'),window.location.reload()" id="delete">Delete</button>
            <button onclick="goToCreatePollPage('{{eventID}}')" id="createPoolButton">Create Pool</button>
        </p>
    </li>
</ul>
<script>


    function goToCreatePollPage(x) {
        location.href = "<c:url value='/app/jsp/poll.jsp?id='/>" + x;
    }

    function goToTheMainPage() {
        location.href = "<c:url value='/app/jsp/start.jsp'/>"
    }

    function getEventsFromDB() {
        fetch('/api/event/getEvents', {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (events) {
            console.log(events);
            if (events.length > 0) {
                document.getElementById("eventElement").classList.remove("w3-hide");
                w3DisplayData("myevent-list", new EventList(events));
            }
        })
    }

    function goToEditPage(x) {
        location.href = "<c:url value='/app/jsp/add-event.jsp?id='/>" + x;
    }

    function deleteEvent(x) {
        fetch('/api/event/' + x + '/delete/', {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            if (response.status === 200) {
                location.reload();
            }
        })
    }

    class EventList {
        constructor(events) {
            this.eventList = events;
        }
    }
</script>

</body>
</html>
