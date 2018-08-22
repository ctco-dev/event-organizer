package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class EventListDto {
    private List<EventDto> eventList;

    public EventListDto(List<EventDto> eventList) {
        this.eventList = eventList;
    }

    public List<EventDto> getEventList() {
        return eventList;
    }
}
