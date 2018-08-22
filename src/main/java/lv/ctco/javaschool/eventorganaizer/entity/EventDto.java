package lv.ctco.javaschool.eventorganaizer.entity;

/**
 * Created by alisa.prudtskih01 on 8/22/2018.
 */
public class EventDto {
    private String eventName;
    private String eventDescription;
    private String eventDate;

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
