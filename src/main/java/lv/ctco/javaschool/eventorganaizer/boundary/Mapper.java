package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.eventorganaizer.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public List<AnswerDto> toAnswersDtoList(List<Answer> list) {
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AnswerDto toDto(Answer answer) {
        AnswerDto r = new AnswerDto();
        r.setText(answer.getText());
        r.setThisAnswerID(answer.getId());
        return r;
    }
    public TopicDto eventToTopicDto(Event event )
    {
        TopicDto topic=new TopicDto();
        topic.setTopicName(event.getName());
        topic.setTopicAuthor(event.getAuthor().getUsername());
        topic.setDate(event.getDate());
        topic.setId(event.getId());
        topic.setStatus(event.getStatus());
        return topic;
    }
    public List<PollDto> mapPollToDto(List<Poll> poll) {
        return poll.stream()
                .map(p -> new PollDto(p.getQuestion(),
                        toAnswersDtoList(p.getAnswers()),
                        p.isFeedback(),
                        p.getEventID(),
                        p.getId()))
                .collect(Collectors.toList());
    }

    public List<Answer> mapPollToAnswerList(Poll poll, PollDto pollDto) {
        List<AnswerDto> answersString = pollDto.getAnswers();
        List<Answer> answerList = new ArrayList<>();
        for (AnswerDto answerDto : answersString) {
            Answer answer = new Answer();
            answer.setPoll(poll);
            answer.setText(answerDto.getText());
            answerList.add(answer);
        }
        return answerList;
    }

    public List<AnswerDto> mapAnswerToAnswerDto(List<Answer> answerList) {
        List<AnswerDto> answerDtos = new ArrayList<>();
        answerList.forEach(al -> {
            AnswerDto a = new AnswerDto();
            a.setCounter(al.getCounter());
            a.setThisAnswerID(al.getId());
            answerDtos.add(a);
        });
        return answerDtos;
    }

    public List<FeedbackDto> mapFeedbackToFeedbackDto(List<Feedback> feedbacks) {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
            feedbacks.forEach(a -> {
            FeedbackDto feedbackDto = new FeedbackDto();
            feedbackDto.setFeedbackId(a.getFeedbackId());
            feedbackDto.setEventID(a.getEvent().getId());
            feedbackDto.setFeedbackAuthor(a.getFeedbackAuthor());
            feedbackDto.setFeedbackText(a.getFeedbackText());
            feedbackDtos.add(feedbackDto);
        });
        return feedbackDtos;
    }
}