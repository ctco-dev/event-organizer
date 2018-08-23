<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Event</title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<style>
    textarea {

        height: 20px;
        width: 200px;
    }
</style>
<header>Add New Event</header>
<form method="post">
    <p><b>Name of Event</b></p>
    <p><textarea name="text" id="name"></textarea></p>
    <p><b>Description</b></p>
    <p><textarea name="text" id="description"></textarea></p>
    <p><b>Date</b>></p>
    <p><input type="text" id="datepicker"></p>
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
    var data = {};

    function getDataFromField() {
        var name = document.getElementById("name");
        data["name"] = name.value;
        var datepciker = document.getElementById("datepicker");
        data["datepicker"] = datepciker.value;
        var description=document.getElementById("description");
        data["description"] = description.value;
    }


    function saveDataToDB() {
        getDataFromField();
        fetch("<c:url value='/api/event/save'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        }).then(function (response) {
            console.log(JSON.stringify(data));
            console.log("DONE");
            location.href = "<c:url value='/app/start.jsp'/>";
        });


    }



    function getEventDataFromDB(){
        fetch("<c:url value='/api/event/getevents'/>", {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (events) {
            console.log(events);
        var event = decodeURIComponent(location.search.substr(1)).split('&');
        event.splice(0, 1);
        var result = event[0];
        console.log(result);
        //document.getElementById("result").innerHTML = result;
    }



    $(function () {
        $( "#datepicker" ).datepicker();
    } );
</script>
</html>
