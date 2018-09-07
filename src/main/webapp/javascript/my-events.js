function goToCreatePollPage(x) {
    location.href = '/app/poll.jsp?id=' + x;
}

function goToTheMainPage() {
    location.href = '/app/start.jsp';
}

function getEventsFromDB() {
    fetch('/api/event/getEvents', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (events) {
        if (events.length > 0) {
            document.getElementById("eventElement").classList.remove("w3-hide");
            w3DisplayData("myevent-list", new EventList(events));
        }
    })
}

function goToEditPage(x) {
    location.href = '/app/add-event.jsp?id=' + x;
}

function deleteEvent(x) {
    fetch('/api/event/' + x + '/delete/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        }
    })
}

function EventList(events) {
    this.eventList = events;
}