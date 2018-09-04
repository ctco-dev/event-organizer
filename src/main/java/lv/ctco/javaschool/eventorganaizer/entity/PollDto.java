package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class PollDto {
    private String question;
    private List<AnswerDto> answers;
    private boolean isFeedback;
    private Long eventID;
    private Long id;

    public PollDto() {

    }

    public PollDto(String question, List<AnswerDto> answers, boolean isFeedback, Long eventID, Long id) {
        this.question = question;
        this.answers = answers;
        this.isFeedback = isFeedback;
        this.eventID = eventID;
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public boolean isFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(boolean feedback) {
        this.isFeedback = feedback;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

