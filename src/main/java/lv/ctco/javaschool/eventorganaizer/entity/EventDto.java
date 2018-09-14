package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class EventDto {
    private String name;
    private String description;
    private String date;
    private String time;
    private Long id;
    private String agenda;
    private EventStatus status;

    public EventDto() {
    }

    public EventDto(String name, String description, String date, String time, Long id, String agenda, EventStatus status) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.id = id;
        this.agenda = agenda;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
