package lv.ctco.javaschool.eventorganaizer.entity;


import java.util.Date;

public class TopicDto {
    private String topicName;
    private String topicAuthor;
    private Date date;
    private boolean isFeedbackPool;

    public TopicDto(String topicName, String topicAuthor, Date date, boolean isFeedbackPool) {
        this.topicName = topicName;
        this.topicAuthor = topicAuthor;
        this.date = date;
        this.isFeedbackPool = isFeedbackPool;
    }

}
