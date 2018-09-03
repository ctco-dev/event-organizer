<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add/Edit Event</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="../styles/pagesStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.css" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timepicker/1.10.0/jquery.timepicker.js"></script>
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
    <p><b>Time</b></p>
    <p><input type="text" id="timepicker"></p>
    <p id="setStatus"><b>Set status</b></p>
    <p id="statuses"><b><input type="checkbox" id="open">Open </b><b><input type="checkbox" id="closed">Closed</b> <b><input type="checkbox" id="finished">Finished</b></p>
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
        data["name"] = (name.value).trim();
            if(name === "" || name === " ") {
                return;
            }
        var description = document.getElementById("description");
        data["description"] = (description.value).trim();
            if(description === "" || description === " ") {
               return;
            }
        var agenda = document.getElementById("agenda");
        data["agenda"] = (agenda.value).trim();
            if(agenda === "" || agenda === " ") {
                return;
            }
        var eventdate = document.getElementById("datepicker");
        data["date"] = eventdate.value;
            if((eventdate.value) === "") {
                return;
            }
        var eventtime=document.getElementById("timepicker");
        data["time"] = (eventtime.value).trim();
            if((eventtime.value) === "" || (eventtime.value) === " ") {
                return;

                var statusClosed=document.getElementById("closed");
                var statusFinished=document.getElementById("finished");
                var statusOpen=document.getElementById("open");
                if(statusClosed.checked){
                    data["status"]="CLOSED"
                }
                if(statusFinished.checked){
                    data["status"]="FINISHED"
                }
                if(statusOpen.checked){
                    data["status"]="OPEN"
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
            document.getElementById("save").classList.add("w3-hide")
            document.getElementById("update").classList.remove("w3-hide");
            document.getElementById("setStatus").classList.remove("w3-hide");
            document.getElementById("statuses").classList.remove("w3-hide");
            document.getElementById("save").classList.add("w3-hide");
            document.getElementById("add").classList.add("w3-hide");



        }
        else {
            document.getElementById("add").classList.remove("w3-hide");
            document.getElementById("edit").classList.add("w3-hide");
            document.getElementById("update").classList.add("w3-hide");
            document.getElementById("save").classList.remove("w3-hide");
            document.getElementById("update").classList.add("w3-hide");
            document.getElementById("setStatus").classList.add("w3-hide");
            document.getElementById("statuses").classList.add("w3-hide");
        }
    }

    function saveDataToDB() {
        getDataFromField();
        if(data.name == "" || data.name == " ")  {
            alert("Please input Event Name");
            return;
        } else {
            if (data.description == "" || data.description == " ") {
                alert("Please input Event Description");
                return;
            } else {
                if (data.agenda == "" || data.agenda == " ") {
                    alert("Please input Event Agenda");
                    return;
                } else {
                    if (data.date == "") {
                        alert("Please input Event Date");
                        return;
                    } else {
                        if (data.time == "" || data.time == " ") {
                            alert("Please input Event Time");
                            return;
                        }
                    }
                }
            }
        }

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
            document.getElementById("timepicker").value = event.eventTime;
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
        $( "#datepicker" ).datepicker();
        $( "#timepicker" ).timepicker();
    });

</script>
</html>
