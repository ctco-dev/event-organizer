package lv.ctco.javaschool.eventorganaizer.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

public class EventDto {
    private String eventName;
    private String eventDescription;
    private String eventDate;
    private long eventID;

    public EventDto(String name, String description, String date, Long id) {
        this.eventName = name;
        this.eventDescription = description;
        this.eventDate = date;
        this.eventID = id;
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
