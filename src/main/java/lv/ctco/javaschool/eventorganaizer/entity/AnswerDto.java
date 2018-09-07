package lv.ctco.javaschool.eventorganaizer.entity;

public class AnswerDto {
    private Long thisAnswerID;
    private int counter;
    private String text;

    public AnswerDto() {
    }

    public AnswerDto(Long answerID, int counter) {
        this.thisAnswerID = answerID;
        this.counter = counter;
    }

    public Long getThisAnswerID() {
        return thisAnswerID;
    }

    public void setThisAnswerID(Long thisAnswerID) {
        this.thisAnswerID = thisAnswerID;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
