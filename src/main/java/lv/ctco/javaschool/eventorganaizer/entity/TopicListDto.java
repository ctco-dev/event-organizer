package lv.ctco.javaschool.eventorganaizer.entity;

import java.util.List;

public class TopicListDto {
    private List<TopicDto> topicList;

    public TopicListDto(List<TopicDto> topicList) {
        this.topicList = topicList;
    }

    public List<TopicDto> getTopicList() {
        return topicList;
    }
}
