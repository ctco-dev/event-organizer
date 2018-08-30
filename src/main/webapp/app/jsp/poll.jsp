<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <title id="title">Poll Event</title>
</head>
<body onload="init()">
<header id="header"><h1>Event Poll</h1></header>
<div id="event-field" class="w3-hide">
    <h2>Event name: {{eventName}}</h2>
    <h4>Event data: {{eventDate}}</h4>
    <h5>Event ID: {{eventID}}</h5>
</div>

<form name="pollform" method="post" style="padding: 15px">
    <p><b>question</b></p>
    <p><textarea name="question" id="question"></textarea></p>
    <p><b>answers</b></p>
    <p><textarea name="answers" id="answers"></textarea></p>
    <p><b>isFeedback</b></p>
    <p><input type="checkbox" id="isFeedback"></p>
</form>
<p>
    <button onclick="savePollToDB()" style="margin: 0px 0px 5px 15px">Create new Poll</button>
    <button onclick="getPollFromDB()" style="margin: 0px 0px 5px 15px">Show Poll</button>
</p>

<script id="pollList" type="text/x-handlebars-template">
    {{#pollArray}}
    <div>
        <p><b>question</b></p>
        <h2>question: {{question}}</h2>
        <p><b>answers</b></p>
        {{#answers}}
        <div>
            <input type="radio" name="quest{{../id}}" value="{{thisAnswerID}}" id="{{thisAnswerID}}"><label for="{{thisAnswerID}}">{{text}}</label>
        </div>
        {{/answers}}
        <p><b>isFeedback</b></p>
        <h2>isFeedback: {{feedback}}</h2>
        <button onclick="deletePoll('{{id}}')">Delete Poll</button>
        <hr/>
    </div>
    {{/pollArray}}
</script>

<div id="displayPoll">
</div>

<script>
    var id = getQueryVariable("id");

    function splitAnswers() {
        var answers = document.getElementById("answers");
        var result = [];
        answers.value.split("\n").forEach(function (txt) {
            var dto = {text: txt};
            result.push(dto)
        });
        return result;
    }

    function buildData() {
        var question = document.getElementById("question");
        var data = {};
        data["question"] = question.value;
        //var answers = document.getElementById("answers");
        data["answers"] = splitAnswers();
        var isFeedback = document.getElementById("isFeedback");
        data["isFeedback"] = isFeedback.checked;
        console.log(data);
        return data;
    }

    function savePollToDB() {
        var data = buildData();
        console.log(data);
        fetch("<c:url value='/api/event/savePoll/'/>" + id, {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        }).then(function (response) {
            getPollFromDB();
            document.getElementById("question").value = '';
            document.getElementById("answers").value = '';
        });
    }

    function getPollFromDB() {
        fetch("<c:url value='/api/event/getPoll/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (poll) {
            if (poll.length === 0) {
                document.getElementById("displayPoll").classList.add("w3-hide");
            } else {
                document.getElementById("displayPoll").classList.remove("w3-hide");
                var context = {pollArray: poll};
                console.log(context);
                var source   = document.getElementById("pollList").innerHTML;
                var template = Handlebars.compile(source);
                var html = template(context);
                document.getElementById("displayPoll").innerHTML = html;
            }
        })
    }

    function deletePoll(x) {
        fetch("<c:url value='/api/event/deletePoll/'/>" + x, {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            getPollFromDB();
        })

    }

    function loadEvent() {
        fetch("<c:url value='/api/event/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (event) {
            console.log(JSON.stringify(event));
            if (event !== undefined) {
                document.getElementById("event-field").classList.remove("w3-hide");
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

    function init() {
        loadEvent();
        getPollFromDB();
    }

</script>
</body>
</html>