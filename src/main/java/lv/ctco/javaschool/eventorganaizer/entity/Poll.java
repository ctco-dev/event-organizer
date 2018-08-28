package lv.ctco.javaschool.eventorganaizer.entity;


import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;

@Entity
public class Poll {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Event eventId;

    private String question;
    private String answers;
    private boolean isFeedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return eventId;
    }

    public void setEvent(Event event) {
        this.eventId = event;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public boolean isFeedback() {
        return isFeedback;
    }

    public void setFeedback(boolean feedback) {
        isFeedback = feedback;
    }
}
