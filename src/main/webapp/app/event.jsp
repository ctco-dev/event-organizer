<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title id="title">{{eventName}}</title>
</head>
<style>
    #displayInfo {
        float: inherit;
        padding: inherit;
    }

    #eventName-field {
        align-content: center;
    }
    #header{
        border: dotted;
        text:bold;
        text-align: center;
    }
</style>
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
        })

    }

    function addEvent() {
        location.href = "/app/add-event.jsp"
    }
    function main() {
        location.href = "/app/start.jsp"
    }

    function myEvents() {
        location.href = "/app/my-events.jsp"
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
