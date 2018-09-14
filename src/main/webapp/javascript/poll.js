var id = getQueryVariable("id");

function splitAnswers() {
    var answers = document.getElementById("answers");
    var result = [];
    answers.value.split("\n").forEach(function (txt) {
        var dto = {text: txt};
        result.push(dto)
    });
    return result;
}
function buildData() {
    var question = document.getElementById("question");
    var data = {};
    data["question"] = question.value;
    data["answers"] = splitAnswers();
    var isFeedback = document.getElementById("isFeedback");
    data["isFeedback"] = isFeedback.checked;
    return data;
}

function savePollToDB() {
    var data = buildData();
    fetch('/api/event/' + id + '/savePoll/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }, body: JSON.stringify(data)
    }).then(function (response) {
        getPollFromDB();
        document.getElementById("question").value = '';
        document.getElementById("answers").value = '';
    });
}

function getPollFromDB() {
    fetch('/api/event/' + id + '/getPoll/', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (poll) {
        if (poll.length === 0) {
            document.getElementById("displayPoll").classList.add("w3-hide");
        } else {
            document.getElementById("displayPoll").classList.remove("w3-hide");
            var context = {pollArray: poll};
            var source = document.getElementById("pollList").innerHTML;
            var template = Handlebars.compile(source);
            document.getElementById("displayPoll").innerHTML = template(context);
        }
    })
}

function deletePoll(x) {
    fetch('/api/event/' + x + '/deletePoll/', {
        "method": "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        getPollFromDB();
    })

}

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
            w3.displayObject("event-field", event);
        }
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

function goToTheMainPage() {
    location.href = '/app/start.jsp';
}

function init() {
    loadEvent();
    getPollFromDB();
}
