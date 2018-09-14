package lv.ctco.javaschool.eventorganaizer.entity;

public class TopicDto {
    private String topicName;
    private String topicAuthor;
    private String date;
    private Long id;
    private EventStatus status;

    public TopicDto(){

    }

    public TopicDto(String topicName, String topicAuthor, String date, Long id, EventStatus status) {
        this.topicName = topicName;
        this.topicAuthor = topicAuthor;
        this.date = date;
        this.id = id;
        this.status = status;
    }

    public TopicDto(Event event) {
        this.topicName = event.getName();
        this.topicAuthor = event.getAuthor().getUsername();
        this.date = event.getDate();
        this.id = event.getId();
        this.status = event.getStatus();
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicAuthor() {
        return topicAuthor;
    }

    public void setTopicAuthor(String topicAuthor) {
        this.topicAuthor = topicAuthor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

}
