package lv.ctco.javaschool.eventorganaizer.entity;

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
    private String feedbackText;

    public Feedback() {
    }

    public Feedback (Event event, String feedbackAuthor, String feedbackText){
        this.event = event;
        this.feedbackAuthor = feedbackAuthor;
        this.feedbackText = feedbackText;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackAuthor() {
        return feedbackAuthor;
    }

    public void setFeedbackAuthor(String feedbackAuthor) {
        this.feedbackAuthor = feedbackAuthor;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}