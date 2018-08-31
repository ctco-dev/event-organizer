<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
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
<div id="event-field" class="w3-hide">
    <h1>{{eventName}}</h1>
    <h4>{{eventDate}}</h4>
    <p>{{eventDescription}}</p>
    <p>{{eventAgenda}}</p>
</div>


<script id="pollList" type="text/x-handlebars-template">
    {{#pollArray}}
    <div>
        <p><b>Question</b></p>
        <h2>{{question}}</h2>
        <p><b>Answers:</b></p>
        {{#answers}}
        <div>
            <input type="radio" name="quest{{../id}}" value="{{thisAnswerID}}" id="{{thisAnswerID}}"><label
                for="{{thisAnswerID}}">{{text}}</label>
        </div>
        {{/answers}}
        <hr/>
    </div>
    {{/pollArray}}
</script>
<div id="feedback" class="w3-hide">
    FEEDBACK

</div>
<div id="voting" class="w3-hide">
    VOTING
</div>

<script>
    var id = getQueryVariable("id");

    function loadEvent() {
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

            if (event.eventStatus == "OPEN") {
                document.getElementById("voting").classList.remove("w3-hide");
                document.getElementById("feedback").classList.add("w3-hide");
                getVotingFromDB();
            }
            if (event.eventStatus == "CLOSED") {
                document.getElementById("voting").classList.add("w3-hide");
                document.getElementById("feedback").classList.remove("w3-hide")
                getFeedbackFromDB()
            }
        })
    }

    function getFeedbackFromDB() {
        fetch("<c:url value='/api/event/getFeedbackPoll/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (poll) {
            if (poll.length === 0) {
                document.getElementById("feedback").classList.add("w3-hide");
            } else {
                document.getElementById("feedback").classList.remove("w3-hide");
                var context = {pollArray: poll};
                var source = document.getElementById("pollList").innerHTML;
                var template = Handlebars.compile(source);
                var html = template(context);
                document.getElementById("feedback").innerHTML = html;
            }
        })
    }

    function getVotingFromDB() {
        fetch("<c:url value='/api/event/getVotingPoll/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (poll) {
            if (poll.length === 0) {
                document.getElementById("voting").classList.add("w3-hide");
            } else {
                document.getElementById("voting").classList.remove("w3-hide");
                var context = {pollArray: poll};
                var source = document.getElementById("pollList").innerHTML;
                var template = Handlebars.compile(source);
                var html = template(context);
                document.getElementById("voting").innerHTML = html;

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
