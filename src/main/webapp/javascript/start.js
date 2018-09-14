function addEvent() {
    location.href = "/app/add-event.jsp"
}
function myEvents() {
    location.href = "/app/my-events.jsp"
}
function checkTopics() {
    fetch('/api/event/getNotEmptyEvents', {
        "method": "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (topics) {
            console.log(topics);
            if (topics.topicList.length > 0) {
                document.getElementById("topic-list").classList.remove("w3-hide");
                w3DisplayData("topic-list", topics);
            }
    })
}

function logout() {
    fetch('/api/auth/logout', {"method": "POST"})
        .then(function (response) {
            location.href = "/";
        });
}