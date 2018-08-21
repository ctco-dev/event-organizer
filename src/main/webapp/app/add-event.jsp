<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Event</title>
</head>
<body>
<style>
    textarea {

        height: 20px;
        width: 200px;
    }
</style>
<header>Add New Event</header>
<form action="textarea1.php" method="post">
    <p><b>Name of Event</b></p>
    <p><textarea name="text"></textarea></p>
    <p><b>Description</b></p>
    <p><textarea name="text"></textarea></p>
    <p><b>Date</b>></p>
    <p><textarea name="text"></textarea></p>
    <p><b>Time</b></p>
    <p><textarea name="text"></textarea></p>
    <p><b>Agenda</b></p>
    <p><textarea name="text"></textarea></p>
</form>
<p>
    <button type="submit" onclick="saveDataToDB()">Save</button>
    <button onclick="goToTheMainPage()">Cancel</button>

</p>
</body>

<script>
    function goToTheMainPage() {
        location.href = "<c:url value='/app/start.jsp'/>";
    }

    function saveDataToDB() {

    }
</script>
</html>
