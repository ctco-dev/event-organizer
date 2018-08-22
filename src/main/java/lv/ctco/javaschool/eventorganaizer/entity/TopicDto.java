package lv.ctco.javaschool.eventorganaizer.entity;


public class TopicDto {
    private String topicName;
    private String topicAuthor;
    private String date;
    private boolean isFeedbackPool;

    public TopicDto(String topicName, String topicAuthor, String date, boolean isFeedbackPool) {
        this.topicName = topicName;
        this.topicAuthor = topicAuthor;
        this.date = date;
        this.isFeedbackPool = isFeedbackPool;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicAuthor() {
        return topicAuthor;
    }

    public String getDate() {
        return date;
    }

    public boolean isFeedbackPool() {
        return isFeedbackPool;
    }
}
