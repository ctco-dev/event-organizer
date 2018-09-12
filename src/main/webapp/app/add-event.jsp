<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add/Edit Event</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="../style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.css" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.js"></script>
    <script src="../javascript/add-event.js"></script>
</head>
<body onload="switchPageStatus()">
<header id="add" class="w3-hide"><h1>Add New Event</h1></header>
<header id="edit" class="w3-hide"><h1>Edit Event</h1></header>
<form name="eventform" method="post" style="padding: 15px">
    <p><b>Name of Event</b></p>
    <p><textarea name="name" id="name"></textarea></p>
    <p><b>Description</b></p>
    <p><textarea name="desc" id="description"></textarea></p>
    <p><b>Agenda</b></p>
    <p><textarea name="agenda" id="agenda"></textarea></p>
    <p><b>Date</b></p>
    <p><input type="text" id="datepicker"></p>
    <p><b>Time</b></p>
    <p><input type="text" id="timepicker"></p>
    <p id="setStatus"><b>Set status</b></p>
    <p id="statuses">
        <input type="radio" id="open" name="event_status" value="open" title="open"/>Open<br/>
        <input type="radio" id="finished" name="event_status" value="finished" title="finished"/>Finished</br>
        <input type="radio" id="closed" name="event_status" value="closed" title="closed"/>Closed
    </p>
</form>
<div id="buttons">
    <button class="w3-button w3-section w3-teal w3-ripple" type="submit" id="save" onclick="saveData()">Save</button>
    <button class="w3-button w3-section w3-teal w3-ripple" type="submit" id="update" onclick="updateData()">Update</button>
    <button class="w3-button w3-section w3-teal w3-ripple" onclick="goToTheMainPage()">Cancel</button>
</div>
</body>
</html>
