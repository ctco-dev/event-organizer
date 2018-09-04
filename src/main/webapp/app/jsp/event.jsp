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

<script>
    var id = getQueryVariable("id");

    function loadEvent() {
        fetch('/api/event/' + id, {
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
                w3.displayObject("title", event);
                w3.displayObject("event-field", event);
            }
            if (event.eventStatus === "OPEN") {
                document.getElementById("voting").classList.remove("w3-hide");
                document.getElementById("feedback").classList.add("w3-hide");
                getVotingPoll();
            }
            if (event.eventStatus === "CLOSED") {
                document.getElementById("voting").classList.add("w3-hide");
                document.getElementById("feedback").classList.remove("w3-hide");
                getFeedbackPoll()
            }
        })
    }

    function getFeedbackPoll() {
        fetch('/api/event/' + id + '/getFeedbackPoll/', {
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
                document.getElementById("feedback").innerHTML = template(context);
            }
        })
    }

    function getVotingPoll() {
        fetch('/api/event/' + id + '/getVotingPoll/', {
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
                document.getElementById("voting").innerHTML = template(context);

            }
        })

    }

    function vote(qid) {
        var checked = document.querySelector('input[name=quest' + qid + ']:checked');
        var checkedAddr = checked.id;
        console.log("checked:" + checkedAddr);
        fetch('/api/event/' + checkedAddr + '/vote/', {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            showStatistics(qid);
            hideVotes();
        });
    }

    function showStatistics(x) {
        fetch('/api/event/' + x + '/getVotes/', {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (answers) {
            console.log(answers);
            document.getElementById("votes").value = answers.answerCounter;
        });

    }


    function hideVotes() {
        document.getElementById("votes").classList.remove("w3-hide");
        document.getElementById("voteButton").classList.add("w3-hide");
        console.log("DONE");
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
            if (pair[0] === variable) {
                return pair[1];
            }
        }
        return (false);
    }
</script>
</body>
</html>
