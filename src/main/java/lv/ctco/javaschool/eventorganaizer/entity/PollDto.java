package lv.ctco.javaschool.eventorganaizer.entity;

public class PollDto {

    private String pollquestion;
    private String pollanswers;
    private boolean pollIsFeedback;
    private Long pollEventID;
    private Long pollID;

    public PollDto(){

    }

    public PollDto(String question, String answers, boolean isFeedback, Long eventID, Long id) {
        this.pollquestion = question;
        this.pollanswers = answers;
        this.pollIsFeedback = isFeedback;
        this.pollEventID = eventID;
        this.pollID = id;
    }

    public String getPollquestion() {
        return pollquestion;
    }

    public void setPollquestion(String pollquestion) {
        this.pollquestion = pollquestion;
    }

    public String getPollanswers() {
        return pollanswers;
    }

    public void setPollanswers(String pollanswers) {
        this.pollanswers = pollanswers;
    }

    public boolean isPollIsFeedback() {
        return pollIsFeedback;
    }

    public void setPollIsFeedback(boolean pollIsFeedback) {
        this.pollIsFeedback = pollIsFeedback;
    }

    public Long getPollEventID() {
        return pollEventID;
    }

    public void setPollEventID(Long pollEventID) {
        this.pollEventID = pollEventID;
    }

    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }
}

