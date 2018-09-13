<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Event Organizer</title>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../style.css">
    <script src="../javascript/start.js"></script>
</head>
<body onload="checkTopics()">
<div class="start-page">
    <button type="button" class="w3-button w3-section w3-teal w3-ripple" onclick="logout()">Log out</button>
    <button type="button" class="w3-button w3-section w3-teal w3-ripple" onclick="addEvent()">Add Event</button>
    <button type="button" class="w3-button w3-section w3-teal w3-ripple" onclick="myEvents()">My Events</button>
</div>
<header id="header"><h1>Our Events</h1></header>
<ul id="topic-list" class="w3-hide">
    <li w3-repeat="topicList">
        <div style="float: left"><a class="topic-list-a" href="<c:url value='/app/event.jsp'/>?id={{id}}">{{topicName}}</a></div>
        <div id="topic-id">{{status}}</div>
        <div style="float: right">{{topicAuthor}}-</div>
    </li>
</ul>
</body>
</html>