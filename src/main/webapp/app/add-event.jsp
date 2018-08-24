<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add/Edit Event</title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body onload="checkFunction()">
<style>
    textarea {

        height: 20px;
        width: 200px;
    }
</style>
<header>Add New Event</header>
<form name="eventform" method="post">
    <p><b>Name of Event</b></p>
    <p><textarea name="name" id="name"></textarea></p>
    <p><b>Description</b></p>
    <p><textarea name="desc" id="description"></textarea></p>
    <p><b>Date</b>></p>
    <p><input name="date" id="datepicker"></p>
</form>
<p>
    <button type="submit" onclick="saveDataToDB()">Save</button>
    <button onclick="goToTheMainPage()">Cancel</button>

</p>
</body>

<script>
    var data = {};
    var id = getQueryVariable("id");

    function goToTheMainPage() {
        location.href = "<c:url value='/app/start.jsp'/>";
    }

    function getDataFromField() {
        var name = document.getElementById("name");
        data["name"] = name.value;
        var datepciker = document.getElementById("datepicker");
        data["datepicker"] = datepciker.value;
        var description = document.getElementById("description");
        data["description"] = description.value;
        if (id) {
            data["id"] = id;
        }
    }

    function checkFunction() {
        if (id) {
            getEventDataFromDB();
        }
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
            location.href = "<c:url value='/app/start.jsp'/>";
        });


    }


    function getEventDataFromDB() {
        var id = getQueryVariable("id");
        console.log(id);
        fetch("<c:url value='/api/event/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (event) {
            console.log(event);
            document.getElementById("name").value = event.eventName;
            document.getElementById("description").value = event.eventDescription;
            document.getElementById("datepicker").value = event.eventDate;
        })
    }

    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] === variable) {
                return pair[1];
            }
        }
        return (false);
    }

    $(function () {
        $("#datepicker").datepicker();
    });
</script>
</html>
