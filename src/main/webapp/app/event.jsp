<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title id="title">{{name}}</title>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../javascript/event.js"></script>
    <link rel="stylesheet" type="text/css" href="../style.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body onload="loadEvent()">
<div id="menu">
    <button type="button" onclick="main()">Back To Main</button>
    <button type="button" onclick="myEvents()">My Events</button>
    <button type="button" onclick="addEvent()">Add Event</button>
</div>
<div id="event-field" class="w3-hide">
    <h1>{{name}}</h1>
    <div>
        <h4>{{date}} &nbsp</h4>
        <h4>{{time}}</h4>
    </div>
    <p>{{description}}</p>
    <p>{{agenda}}</p>
</div>
<script id="pollList" type="text/x-handlebars-template">
    {{#pollArray}}
    <div>
        <p><b>Question</b></p>
        <h2>{{question}}</h2>
        <p><b>Answers:</b></p>
        {{#answers}}
        <div>
            <input type="radio" name="quest{{../id}}" value="{{thisAnswerID}}" id="{{thisAnswerID}}">
            <label for="{{thisAnswerID}}">{{text}}</label>
            <div class="votes">
                <label for="{{thisAnswerID}}" name="votes{{../id}}"></label>
            </div>
        </div>
        {{/answers}}
        <button id="voteButton{{id}}" class="" onclick="vote({{id}})">VOTE!</button>
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
<div id="feedbackInput" class="w3-hide">
    <label>Feedback:</label><br/>
    <textarea class="form-control" rows="5" id="comment"></textarea></br>
    <button type="submit" id="save" onclick="checkIfConatainsText()">Save</button>
</div>

<script id="feedbackList" type="text/x-handlebars-template">
    {{#feedbackArray}}
    <div>
        <p id="textField{{feedbackId}}">{{feedbackAuthor}}: {{feedbackText}}</p>
        <hr/>
    </div>
    {{/feedbackArray}}
</script>
<div id="feedbackText" class="w3-hide">
    FEEDBACKTEXT
</div>
</body>
</html>
