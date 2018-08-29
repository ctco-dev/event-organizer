<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://www.w3schools.com/lib/w3.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <title id="title">Poll Event</title>
</head>
<body onload="loadEvent(),getPollFromDB()">
<header id="header"><h1>Event Poll</h1></header>
<div id="event-field" class="w3-hide">
    <h2>Event name: {{eventName}}</h2>
    <h4>Event data: {{eventDate}}</h4>
    <h4>Event ID: {{eventID}}</h4>
</div>

<form name="pollform" method="post" style="padding: 15px">
    <p><b>question</b></p>
    <p><textarea name="question" id="question"></textarea></p>
    <p><b>answers</b></p>
    <p><textarea name="answers" id="answers"></textarea></p>
    <p><b>isFeedback</b></p>
    <p><input type="checkbox" id="isFeedback"></p>

</form>

<div id="displayPoll" >
    <div w3-repeat="pollArray">
        <p><b>question</b></p>
        <h2>question: {{pollquestion}}</h2>
        <p><b>answers</b></p>
        <h2>answers: {{pollanswers}}</h2>
        <hr/>
    </div>

</div>


<p>
      <button onclick="savePollToDB()" style="margin: 0px 0px 5px 15px">Create new Poll</button>
      <button onclick="getPollFromDB()" style="margin: 0px 0px 5px 15px">Show Poll</button>
</p>

<script>
    var data = {};
    var id = getQueryVariable("id");

    function getData() {
        var question = document.getElementById("question");
        data["question"] = question.value;
        var answers = document.getElementById("answers");
        data["answers"] = answers.value;
        var isFeedback = document.getElementById("isFeedback");
        data["isFeedback"] = isFeedback.checked;
    }

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
                w3.displayObject("event-field", event);
            }
        })
    }

    function savePollToDB() {
        getData();
        console.log(data);
        fetch("<c:url value='/api/event/savePoll/'/>"+id, {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        }).then(function (response) {
            getPollFromDB();
            document.getElementById("question").value='';
            document.getElementById("answers").value='';
            document.getElementById("isFeedback").value=false;

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
            var z = {pollArray:poll};
            w3.displayObject("displayPoll", z);
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

</script>
</body>
</html>