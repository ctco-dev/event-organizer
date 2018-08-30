package lv.ctco.javaschool.eventorganaizer.entity;


public class TopicDto {
    private String topicName;
    private String topicAuthor;
    private String date;
    private String path;
    private Long id;

    public TopicDto(Event event) {
        this.topicName = event.getName();
        this.topicAuthor = event.getAuthor().getUsername();
        this.date = event.getDate();
        this.id = event.getId();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
