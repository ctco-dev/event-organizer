<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Event Organizer</title>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
</head>
<body onload="checkTopics()">
<header id="header"><h1>Our Events</h1></header>
<div style="margin: 0px 0px 20px 15px">
    <button type="button" onclick="logout()">Log out</button>
    <button type="button" onclick="addEvent()">Add Event</button>
    <button type="button" onclick="myEvents()">My Events</button>
</div>

<ul id="topic-list" class="w3-hide">
    <li w3-repeat="topicList">
        <div style="float: left"><a href="<c:url value='/app/jsp/event.jsp'/>?id={{id}}">{{topicName}}</a> </div>
        <div style="float: right; padding-right: 15px">{{topicAuthor}}</div>
        <div style="border-style: none none solid none; border-color: white"></div>
    </li>
</ul>
<script>
    function addEvent() {
        location.href = "/app/jsp/add-event.jsp"
    }
    function myEvents() {
        location.href = "/app/jsp/my-events.jsp"
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