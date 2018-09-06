package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class EventDto {
    private String eventName;
    private String description;
    private String eventDate;
    private String eventTime;
    private Long eventID;
    private String agenda;
    private EventStatus eventStatus;
//    private List<Feedback> eventFeedback;

    public EventDto() {
    }

    public EventDto(String name, String description, String date, String time, Long id, String agenda, EventStatus status
//            , List<Feedback> feedbacks
    ) {
        this.eventName = name;
        this.description = description;
        this.eventDate = date;
        this.eventTime = time;
        this.eventID = id;
        this.agenda = agenda;
        this.eventStatus = status;
//        this.eventFeedback = feedbacks;
    }

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

//    public List<Feedback> getEventFeedback() {
//        return eventFeedback;
//    }
//
//    public void setEventFeedback(List<Feedback> eventFeedback) {
//        this.eventFeedback = eventFeedback;
//    }
}
