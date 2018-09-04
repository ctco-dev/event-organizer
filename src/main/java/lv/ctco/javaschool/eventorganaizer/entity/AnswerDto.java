package lv.ctco.javaschool.eventorganaizer.entity;

public class AnswerDto {
    private Long thisAnswerID;
    private int answerCounter;
    private String text;

    public AnswerDto() {
    }

    public AnswerDto(Long answerID, int counter) {
        this.thisAnswerID = answerID;
        this.answerCounter = counter;
    }

    public Long getThisAnswerID() {
        return thisAnswerID;
    }

    public void setThisAnswerID(Long thisAnswerID) {
        this.thisAnswerID = thisAnswerID;
    }

    public int getAnswerCounter() {
        return answerCounter;
    }

    public void setAnswerCounter(int answerCounter) {
        this.answerCounter = answerCounter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
