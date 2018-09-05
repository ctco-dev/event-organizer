package lv.ctco.javaschool.eventorganaizer.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String eventName;
    private String eventDate;
    private String eventTime;
    private String description;
    private String agenda;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    public Event() {
    }

    public Event(String eventName, User author, String description, String agenda, String eventDate, String eventTime) {
        this.eventName = eventName;
        this.author = author;
        this.description = description;
        this.agenda = agenda;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.status = EventStatus.OPEN;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        this.eventDate = date;
    }

    public String getDescription() {
        return description;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String time) {
        this.eventTime = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }
}