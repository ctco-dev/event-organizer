<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <title id="title">{{eventName}}</title>
</head>
<link rel="stylesheet" type="text/css" href="pagesStyle.css">

<body onload="loadEvent()">
<div id="menu">
    <button type="button" onclick="main()">Back To Main</button>
    <button type="button" onclick="myEvents()">My Events</button>
    <button type="button" onclick="addEvent()">Add Event</button>
</div>
<div id="event-field" class="w3-hide" align="center">
    <h1>{{eventName}}</h1>
    <h4 style="background: #c4e5ff">{{eventDate}}</h4>
    <p style="border: thick">{{eventDescription}}</p>
</div>

<div id="voting" class="w3-hide">
    VOTING
</div>
<div id="feedback" class="w3-hide">
    FEEDBACK
</div>


<script>
    function loadEvent() {
        var id = getQueryVariable("id");
        fetch("<c:url value='/api/event/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then(function (response) {
            return response.json();
        }).then(function (event) {
            console.log(JSON.stringify(event));
            if (event !== undefined) {
                document.getElementById("event-field").classList.remove("w3-hide");
                w3.displayObject("title", event);
                w3.displayObject("event-field", event);
            }

            if(event.eventStatus=="OPEN"){
                document.getElementById("voting").classList.remove("w3-hide")
                document.getElementById("feedback").classList.add("w3-hide")
            }
            if(event.eventStatus=="CLOSED"){
                document.getElementById("voting").classList.add("w3-hide")
                document.getElementById("feedback").classList.remove("w3-hide")
            }
        })

    }

    function addEvent() {
        location.href = "/app/jsp/add-event.jsp"
    }

    function main() {
        location.href = "/app/jsp/start.jsp"
    }

    function myEvents() {
        location.href = "/app/jsp/my-events.jsp"
    }

    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return (false);
    }
</script>
</body>
</html>
