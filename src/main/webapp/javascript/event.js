var id = getQueryVariable("id");

function loadEvent() {
    fetch('/api/event/' + id, {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (event) {
        console.log(JSON.stringify(event));
        if (event !== undefined) {
            document.getElementById("event-field").classList.remove("w3-hide");
            w3.displayObject("title", event);
            w3.displayObject("event-field", event);
        }
        if (event.eventStatus === "OPEN") {
            document.getElementById("voting").classList.remove("w3-hide");
            document.getElementById("feedback").classList.add("w3-hide");
            getVotingPoll();
        } else if (event.eventStatus === "CLOSED") {
            document.getElementById("voting").classList.add("w3-hide");
            document.getElementById("feedback").classList.remove("w3-hide");
            getFeedbackPoll();
        }
    })
}

function getFeedbackPoll() {
    fetch('/api/event/' + id + '/getFeedbackPoll/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (poll) {
        if (poll.length === 0) {
            document.getElementById("feedback").classList.add("w3-hide");
        } else {
            document.getElementById("feedback").classList.remove("w3-hide");
            var context = {pollArray: poll};
            var source = document.getElementById("pollList").innerHTML;
            var template = Handlebars.compile(source);
            document.getElementById("feedback").innerHTML = template(context);
        }
    })
}

function getVotingPoll() {
    fetch('/api/event/' + id + '/getVotingPoll/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (poll) {
        if (poll.length === 0) {
            document.getElementById("voting").classList.add("w3-hide");
        } else {
            document.getElementById("voting").classList.remove("w3-hide");
            var context = {pollArray: poll};
            var source = document.getElementById("pollList").innerHTML;
            var template = Handlebars.compile(source);
            document.getElementById("voting").innerHTML = template(context);
        }
    })
}

function vote(id) {
    var checked = document.querySelector('input[name=quest' + id + ']:checked');
    var checkedAddr = checked.id;
    console.log("checked:" + checkedAddr);
    fetch('/api/event/vote/' + checkedAddr, {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        showStatistics(id);
        hideVotes(id);
    });
}

function showStatistics(x) {
    fetch('/api/event/' + x + '/getVotes/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (answers) {
        console.log(answers);
        var input = document.getElementsByName("quest" + x);
        var element = document.getElementsByName("votes" + x);
        for (i = 0; i < document.getElementsByName("votes" + x).length; i++) {
            input[i].disabled = true;
            element[i].innerHTML = "Votes:" + answers[i].answerCounter;
        }
    });
}

function hideVotes() {
    document.getElementById("voteButton").classList.add("w3-hide");
    console.log("DONE");
}

function addEvent() {
    location.href = "/app/add-event.jsp"
}

function main() {
    location.href = "/app/start.jsp"
}

function myEvents() {
    location.href = "/app/my-events.jsp"
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