<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title id="title">{{eventName}}</title>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../style.css">
    <script src="../javascript/event.js"></script>
</head>
<body onload="loadEvent()">
<div id="menu">
    <button type="button" onclick="main()">Back To Main</button>
    <button type="button" onclick="myEvents()">My Events</button>
    <button type="button" onclick="addEvent()">Add Event</button>
</div>
<div id="event-field" class="w3-hide">
    <h1>{{eventName}}</h1>
    <div>
        <h4>{{eventDate}} &nbsp</h4>
        <h4>{{eventTime}}</h4>
    </div>
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
            <div id="votes" class="w3-hide"><label
                    for="{{thisAnswerID}}">Votes: {{answerCounter}}</label></div>

        </div>
        {{/answers}}
        <button id="voteButton{{../id}}" onclick="vote({{id}}),showStatistics({{id}})">VOTE!</button>
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
</body>
</html>
