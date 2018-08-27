<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Event Organizer</title>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        ul {
            border: solid;
        }
        li{
            border: thin;
            width: auto;
        }

        a {
            underline-mode: none;
        }
        #header{
            border: dotted;
            text:bold;
            text-align: center;
        }
    </style>
</head>
<body onload="checkTopics()">
<header id="header"><h1>Our Events</h1></header>
<div>
    <button type="button" onclick="logout()">Log out</button>
    <button type="button" onclick="addEvent()">Add Event</button>
    <button type="button" onclick="myEvents()">My Events</button>
</div>

<ul id="topic-list" class="w3-hide">
    <li w3-repeat="topicList">
        <div style="float: left"><a href="<c:url value='/app/'/>{{path}}?id={{id}}">{{topicName}}</a> </div>
        <div style="float: right" >{{topicAuthor}}</div>
    </li>
</ul>

<script>
    function addEvent() {
        location.href = "/app/add-event.jsp"
    }
    function myEvents() {
        location.href = "/app/my-events.jsp"
    }
    function checkTopics() {
        fetch("<c:url value='/api/event'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then(function (response) {
            return response.json();
        }).then(function (topics) {
            if (topics.topicList.length > 0) {
                document.getElementById("topic-list").classList.remove("w3-hide");
                w3DisplayData("topic-list", topics);
                console.log(topics)
            }
        })
    }
    function logout() {
        fetch("<c:url value='/api/auth/logout'/>", {"method": "POST"})
            .then(function (response) {
                location.href = "/";
            });
    }
</script>
</body>
</html>