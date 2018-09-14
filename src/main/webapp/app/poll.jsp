<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title id="title">Poll Event</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../style.css">
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
    <script src="../javascript/poll.js"></script>
</head>
<body onload="init()">
<header id="header"><h1>Event Poll</h1></header>
<p class="ml-15">
    <button class="w3-button w3-section w3-teal w3-ripple" onclick="goToTheMainPage()">Main page</button>
</p>
<div id="event-field" class="ml-15 w3-hide">
    <h2>Event name: {{name}}</h2>
    <h4>Event data: {{date}}</h4>
    <h5>Event ID: {{id}}</h5>
</div>
<form name="pollform" method="post" style="padding: 15px">
    <p><b>Question</b></p>
    <p><textarea name="question" id="question"></textarea></p>
    <p><b>Answers</b></p>
    <p><textarea name="answers" id="answers"></textarea></p>
    <p><b>Feedback poll</b></p>
    <p><input type="checkbox" id="isFeedback"></p>
</form>
<p>
    <button class="w3-button w3-section w3-teal w3-ripple" onclick="savePollToDB()" style="margin: 0px 0px 5px 15px">Create new Poll</button>
</p>
<script id="pollList" type="text/x-handlebars-template">
    {{#pollArray}}
    <div>
        <p><b>Question:</b></p>
        <h2>{{question}}</h2>
        <p><b>Answers:</b></p>
        {{#answers}}
        <div>
            <input type="radio" name="quest{{../id}}" value="{{thisAnswerID}}" id="{{thisAnswerID}}">
            <label for="{{thisAnswerID}}">{{text}}</label>
        </div>
        {{/answers}}
        <p><b>Feedback poll:</b></p>
        <h3>{{feedbackText}}</h3>
        <button onclick="deletePoll('{{id}}')">Delete Poll</button>
        <hr/>
    </div>
    {{/pollArray}}
</script>
<div id="displayPoll">
</div>
</body>
</html>