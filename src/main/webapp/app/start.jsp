<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Event Organizer</title>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body onload="checkTopics()">
<button type="button" onclick="logout()">Log out</button>
<div>
    <button type="button" onclick="addEvent()">Add Event</button>
    <button type="button" onclick="myEvents()">My Events</button>
</div>

<ul id="topic-list" class="w3-hide">
    <li w3-repeat="topicList">{{topicName}} - {{topicAuthor}}</li>
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
            "method": "GET"
        }).then(function (response) {
            return response.json();
        }).then(function (topics) {
            console.log(JSON.stringify(topics));
            if (topics.topicList.length > 0) {
                document.getElementById("topic-list").classList.remove("w3-hide");
                w3DisplayData("topic-list", topics);
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
