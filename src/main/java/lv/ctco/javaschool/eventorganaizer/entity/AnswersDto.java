package lv.ctco.javaschool.eventorganaizer.entity;


public class AnswersDto {

    private Long thisAnswerID;
    private int answerCounter;

    public AnswersDto(){

    }

    public AnswersDto(Long answerID, int counter){
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
}
