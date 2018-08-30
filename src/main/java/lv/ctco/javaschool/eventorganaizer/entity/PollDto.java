package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class PollDto {

    private String pollquestion;
    private List<Answer> pollanswers;
    private boolean pollIsFeedback;
    private Long pollEventID;
    private Long pollID;

    public PollDto(){

    }

    public PollDto(String question, List<Answer> answers, boolean isFeedback, Long eventID, Long id) {
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

    public List<Answer> getPollanswers() {
        return pollanswers;
    }

    public void setPollanswers(List<Answer> pollanswers) {
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

