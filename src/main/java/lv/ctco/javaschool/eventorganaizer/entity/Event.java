package lv.ctco.javaschool.eventorganaizer.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private User author;

    private String date;

    private String description;

    private EventStatus status;

    public Event(String name, User author, String description, String date) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.date = date;
        this.status = EventStatus.OPEN;

    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Event() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
