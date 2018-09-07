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
        if (event !== undefined) {
            document.getElementById("event-field").classList.remove("w3-hide");
            w3.displayObject("title", event);
            w3.displayObject("event-field", event);
        }
        if (event.status === "OPEN") {
            document.getElementById("voting").classList.remove("w3-hide");
            document.getElementById("feedback").classList.add("w3-hide");
            getVotingPoll();
            hidePoll(id);
        } else if (event.status === "CLOSED") {
        } else if (event.eventStatus === "FINISHED") {
            document.getElementById("voting").classList.add("w3-hide");
            document.getElementById("feedback").classList.remove("w3-hide");
            getFeedbackPoll();
        }
    })
}
function checkIfConatainsText() {
    var str1 = document.getElementById("comment").value;
    if (document.getElementById("comment").value.replace(/\s/g, '').length) {
        saveTextFeedback()
    }
    else {
        window.alert("Please enter text!");
    }
}
function saveTextFeedback() {
    var feedbackText = document.getElementById("comment").value;
    feedbackText = feedbackText.replace(/(?:\r\n|\r|\n)/g, ' ');
    fetch('/api/event/' + id + '/saveFeedback/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(feedbackText)
    }).then(function (response) {
        console.log(feedbackText);
        document.getElementById("comment").value = "";
        getTextFeedback();
    });
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

function getTextFeedback() {
    fetch('/api/event/' + id + '/getFeedbackText/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (feedback) {
        console.log(JSON.stringify(feedback));
        if (feedback.length === 0) {
            document.getElementById("feedbackText").classList.add("w3-hide");
        } else {
            document.getElementById("feedbackText").classList.remove("w3-hide");
            var context = {feedbackArray: feedback};
            var source = document.getElementById("feedbackList").innerHTML;
            var template = Handlebars.compile(source);
            document.getElementById("feedbackText").innerHTML = template(context);
        }
    })
}


function vote(id) {
    var checked = document.querySelector('input[name=quest' + id + ']:checked');
    var checkedAddr = checked.id;
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
        var input = document.getElementsByName("quest" + x);
        var votes = document.getElementsByName("votes" + x);
        for (i = 0; i < votes.length; i++) {
            input[i].disabled = true;
            votes[i].innerHTML = "Votes:" + answers[i].counter;
        }
    });
}

function hideVotes(x) {
    document.getElementById("voteButton" + x).classList.add("w3-hide");
}

function hidePoll() {
    fetch('/api/event/' + id + '/getAnswers/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (answers) {
        if (Object.keys(answers).length > 0) {
            for (i = 0; i < Object.keys(answers).length; i++) {
                showStatistics(answers[Object.keys(answers)[i]].id);
                hideVotes(answers[Object.keys(answers)[i]].id);
            }
        }
    });
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