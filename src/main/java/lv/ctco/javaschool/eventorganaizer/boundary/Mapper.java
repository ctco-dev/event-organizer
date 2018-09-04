package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.AnswerDto;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;
import lv.ctco.javaschool.eventorganaizer.entity.PollDto;

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

    public List<PollDto> mapPollToDto(List<Poll> poll) {
        return poll.stream()
                .map(p -> new PollDto(p.getQuestion(),
                        toAnswersDtoList(p.getAnswers()),
                        p.isFeedback(),
                        p.getEventID(),
                        p.getId()))
                .collect(Collectors.toList());
    }
}
