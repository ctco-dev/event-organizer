package lv.ctco.javaschool.eventorganaizer.entity;

public class FeedbackDto {

    private Long feedbackId;
    private Long eventID;
    private String feedbackAuthor;
    private String feedbackText;

    public FeedbackDto() {
    }

    public FeedbackDto(Long feedbackId, Long eventID, String feedbackAuthor, String feedbackText) {
        this.feedbackId = feedbackId;
        this.eventID = eventID;
        this.feedbackAuthor = feedbackAuthor;
        this.feedbackText = feedbackText;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
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
}
