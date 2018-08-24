package lv.ctco.javaschool.eventorganaizer.entity;

public class EventDto {
    private long eventID;
    private String eventName;
    private String eventDescription;
    private String eventDate;

    public EventDto() {
    }

    public EventDto(Event e) {
        this.eventDate = e.getDate();
        this.eventName = e.getName();
        this.eventDescription = e.getDescription();
        this.eventID = e.getId();
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
