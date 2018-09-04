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
<p>
    <button onclick="goToTheMainPage()">Go to the main page</button>
</p>
<ul class="w3-ul" id="myevent-list">
    <li w3-repeat="eventList" type="text" id="eventElement" class="w3-hide">
        <a href="<c:url value='/app/event.jsp'/>?id={{eventID}}">{{eventName}}</a>
        <p>
            <button onclick="goToEditPage('{{eventID}}')" id="goToEdit">Edit</button>
            <button onclick="deleteEvent('{{eventID}}'),window.location.reload()" id="delete">Delete</button>
            <button onclick="goToCreatePollPage('{{eventID}}')" id="createPoolButton">Create Pool</button>
        </p>
    </li>
</ul>
</body>
</html>
