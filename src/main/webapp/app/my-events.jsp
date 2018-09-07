<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Events</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" type="text/css" href="../style.css">
    <script src="http://www.w3schools.com/lib/w3data.js"></script>
    <script src="../javascript/my-events.js"></script>
</head>
<body onload="getEventsFromDB()">
<header id="header"><h1>My Events</h1></header>
<p class="ml-15">
    <button class="w3-button w3-section w3-teal w3-ripple" onclick="goToTheMainPage()">Main page</button>
</p>
<ul class="w3-ul" id="myevent-list">
    <li w3-repeat="eventList" type="text" id="eventElement" class="w3-hide">
        <a class="topic-list-a" href="<c:url value='/app/event.jsp'/>?id={{id}}">{{name}}</a>
        <p class="event-list-p">
            <button class="w3-button w3-section w3-teal w3-ripple" onclick="goToEditPage('{{id}}')" id="goToEdit">Edit</button>
            <button class="w3-button w3-section w3-teal w3-ripple" onclick="deleteEvent('{{id}}'),window.location.reload()" id="delete">Delete</button>
            <button class="w3-button w3-section w3-teal w3-ripple" onclick="goToCreatePollPage('{{id}}')" id="createPoolButton">Create Pool</button>
        </p>
    </li>
</ul>
</body>
</html>
