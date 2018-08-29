<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Event Organizer</title>
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="javascript/start-page-logic.js"></script>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <link rel="stylesheet" type="text/css" href="pagesStyle.css">
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
        <div style="float: left"><a href="<c:url value='/app/jsp/event.jsp'/>?id={{id}}">{{topicName}}</a> </div>
        <div style="float: right" >{{topicAuthor}}</div>
    </li>
</ul>

</body>
</html>