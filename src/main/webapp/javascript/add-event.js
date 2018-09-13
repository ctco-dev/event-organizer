var data = {};
var id = getEventIdByUrl("id");

function goToTheMainPage() {
    location.href = '/app/start.jsp';
}

function getDataFromTextarea() {
    var name = document.getElementById("name");
    data.name = (name.value).trim();
    var description = document.getElementById("description");
    data.description = (description.value).trim();
    var agenda = document.getElementById("agenda");
    data.agenda = (agenda.value).trim();
    var eventdate = document.getElementById("datepicker");
    data.date = eventdate.value;
    var eventtime = document.getElementById("timepicker");
    data.time = (eventtime.value).trim();
    var statusClosed = document.getElementById("closed");
    var statusFinished = document.getElementById("finished");
    var statusOpen = document.getElementById("open");
    if (statusClosed.checked) {
        data.status = "CLOSED";
    } else if (statusFinished.checked) {
        data.status = "FINISHED";
    } else if (statusOpen.checked) {
        data.status = "OPEN";
    }
    if (id) {
        data.id = id;
    }
    return data;
}

function checkNonEmptyInput(data) {
    return validateField(data.name, "Name")
        && validateField(data.description, "Description")
        && validateField(data.agenda, "Agenda")
        && validateField(data.date, "Date")
        && validateField(data.time, "Time");
}

function validateField(field, message) {
    if (field === "" || field.trim() === "") {
        alert("Please input Event " + message);
        return false;
    }
    return true;
}

function switchPageStatus() {
    if (id) {
        getEventData();
        document.getElementById("edit").classList.remove("w3-hide");
        document.getElementById("update").classList.remove("w3-hide");
        document.getElementById("save").classList.add("w3-hide");
        document.getElementById("update").classList.remove("w3-hide");
        document.getElementById("setStatus").classList.remove("w3-hide");
        document.getElementById("statuses").classList.remove("w3-hide");
        document.getElementById("save").classList.add("w3-hide");
        document.getElementById("add").classList.add("w3-hide");
    } else {
        document.getElementById("add").classList.remove("w3-hide");
        document.getElementById("edit").classList.add("w3-hide");
        document.getElementById("update").classList.add("w3-hide");
        document.getElementById("save").classList.remove("w3-hide");
        document.getElementById("update").classList.add("w3-hide");
        document.getElementById("setStatus").classList.add("w3-hide");
        document.getElementById("statuses").classList.add("w3-hide");
    }
}

function saveData() {
    var data = getDataFromTextarea();
    if (checkNonEmptyInput(data) === false) {
        return;
    }
    fetch('/api/event/save/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }, body: JSON.stringify(data)
    }).then(function (response) {
        location.href = '/app/start.jsp';
    });
}

function updateData() {
    var data = getDataFromTextarea();
    if (checkNonEmptyInput(data) === false) {
        return;
    }
    fetch('/api/event/update/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }, body: JSON.stringify(data)
    }).then(function (response) {
        location.href = '/app/start.jsp';
    });
}

function getEventData() {
    var id = getEventIdByUrl("id");
    fetch('/api/event/' + id, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        document.getElementById("name").value = event.name;
        document.getElementById("description").value = event.description;
        document.getElementById("datepicker").value = event.date;
        document.getElementById("timepicker").value = event.time;
        document.getElementById("agenda").value = event.agenda;
    })
}

function getEventIdByUrl(id) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] === id) {
            return pair[1];
        }
    }
    return (false);
}

$(function () {
    $("#datepicker").datepicker();
    $("#timepicker").timepicker();
});