package lv.ctco.javaschool.eventorganaizer.entity;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Feedback {

    @Id
    @GeneratedValue
    private Long feedbackId;

    @ManyToOne (cascade = CascadeType.ALL)
    private Event event;

    private String feedbackAuthor;
    private String feedback;

    public Feedback() {
    }

    public Feedback (Event event, String feedbackAuthor, String feedback){
        this.event = event;
        this.feedbackAuthor = feedbackAuthor;
        this.feedback = feedback;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

//    public Long getEventID() {
//        return eventID;
//    }
//
//    public void setEventID(Long eventID) {
//        this.eventID = eventID;
//    }

    public String getFeedbackAuthor() {
        return feedbackAuthor;
    }

    public void setFeedbackAuthor(String feedbackAuthor) {
        this.feedbackAuthor = feedbackAuthor;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}