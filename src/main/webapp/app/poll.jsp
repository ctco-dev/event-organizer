<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title id="title">Poll Event</title>
</head>
<body onload="loadEvent()">
<header id="header"><h1>Event Poll</h1></header>
<div id="event-field" class="w3-hide">
    <h2>Event name:  {{eventName}}</h2>
    <h4>Event data:  {{eventDate}}</h4>
</div>



<p>
    <button onclick="addPoll()">Create new Poll</button>
</p>

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
    function addPoll() {

    }

</script>
</body>
</html>