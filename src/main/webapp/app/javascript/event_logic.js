
 function loadEvent() {
    var id = getQueryVariable("id");
    fetch('/api/event/' + id, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        console.log(JSON.stringify(event));
        if (event !== undefined) {
            document.getElementById("event-field").classList.remove("w3-hide");
            w3.displayObject("title", event);
            w3.displayObject("event-field", event);
        }
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

function addEvent() {
    location.href = "/app/jsp/add-event.jsp"
}

function main() {
    location.href = "/app/jsp/start.jsp"
}

function myEvents() {
    location.href = "/app/jsp/my-events.jsp"
}
