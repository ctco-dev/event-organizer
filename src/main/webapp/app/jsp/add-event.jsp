<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add/Edit Event</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body onload="checkFunction()">
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
    <p><b>Set status</b></p>
    <p><b id="chboxClosed"><input type="checkbox" id="closed">Closed</b> <b id="chboxFinished"><input type="checkbox" id="finished">Finished</b></p>
</form>

<div id="buttons">
    <button type="submit" id="save" onclick="saveDataToDB()">Save</button>
    <button type="submit" id="update" onclick="updateData()">Update</button>
    <button onclick="goToTheMainPage()">Cancel</button>

</div>
</body>

<script>
    var data = {};
    var id = getQueryVariable("id");

    function goToTheMainPage() {
        location.href = "<c:url value='/app/jsp/start.jsp'/>";
    }

    function getDataFromField() {
        var name = document.getElementById("name");
        data["name"] = name.value;
        var eventdate = document.getElementById("datepicker");
        data["date"] = eventdate.value;
        var description = document.getElementById("description");
        data["description"] = description.value;
        var agenda = document.getElementById("agenda");
        data["agenda"] = agenda.value;
        var statusClosed=document.getElementById("closed");
        var statusFinished=document.getElementById("finished");
        if(statusClosed.checked){
            data["status"]="CLOSED"
        }
        if(statusFinished.checked){
            data["status"]="FINISHED"
        }
        if (id) {
            data["id"] = id;
        }
    }

    function checkFunction() {
        if (id) {
            getEventDataFromDB();
            document.getElementById("edit").classList.remove("w3-hide");
            document.getElementById("update").classList.remove("w3-hide");
            document.getElementById("closed").classList.remove("w3-hide");
            document.getElementById("finished").classList.remove("w3-hide");
            document.getElementById("save").classList.add("w3-hide");
            document.getElementById("add").classList.add("w3-hide");



        }
        else {
            document.getElementById("add").classList.remove("w3-hide");
            document.getElementById("edit").classList.add("w3-hide");
            document.getElementById("update").classList.add("w3-hide");
            document.getElementById("save").classList.remove("w3-hide");
            document.getElementById("chboxClosed").classList.add("w3-hide");
            document.getElementById("chboxFinished").classList.add("w3-hide");
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
            location.href = "<c:url value='/app/jsp/start.jsp'/>";
        });
    }

    function updateData() {
        getDataFromField();
        fetch("<c:url value='/api/event/update'/>", {
            "method": "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }, body: JSON.stringify(data)
        }).then(function (response) {
            location.href = "<c:url value='/app/jsp/start.jsp'/>";
        });
    }

    function getEventDataFromDB() {
        var id = getQueryVariable("id");
        fetch("<c:url value='/api/event/'/>" + id, {
            "method": "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.json();
        }).then(function (event) {
            document.getElementById("name").value = event.eventName;
            document.getElementById("description").value = event.eventDescription;
            document.getElementById("datepicker").value = event.eventDate;
            document.getElementById("agenda").value = event.eventAgenda;
        })
    }

    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
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
