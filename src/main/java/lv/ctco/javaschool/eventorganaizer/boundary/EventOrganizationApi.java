package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.eventorganaizer.control.AnswersStore;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.control.PollStore;
import lv.ctco.javaschool.eventorganaizer.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/event")
@Stateless
public class EventOrganizationApi {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;

    @Inject
    private PollStore pollStore;

    @Inject
    private AnswersStore answersStore;


    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public TopicListDto getAllOpenEvents() {
        List<TopicDto> listOfTopicDto = eventStore.getAllEvents()
                .stream()
                .map(TopicDto::new)
                .collect(Collectors.toList());
        return new TopicListDto(listOfTopicDto);
    }

    @POST
    @Path("/save")
    @RolesAllowed({"USER", "ADMIN"})
    public void saveEvent(Event event) {
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(userStore.getCurrentUser());
        em.persist(event);
    }

    @POST
    @Path("/update")
    @RolesAllowed({"USER", "ADMIN"})
    public void updateEvent(Event event) {
        event.setAuthor(userStore.getCurrentUser());
        em.merge(event);
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    public EventDto getEventById(@PathParam("id") Long id) throws IllegalArgumentException {
        Optional<Event> event = eventStore.getEventById(id);
        if (event.isPresent()) {
            Event e = event.get();
            return new EventDto(e.getName(), e.getDescription(), e.getDate(), e.getTime(), e.getId(), e.getAgenda(), e.getStatus());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getevents")
    public List<EventDto> getAllAuthorEvents() {
        List<Event> event = eventStore.getAuthorEvents(userStore.getCurrentUser());

        return event.stream()
                .map(e -> new EventDto(e.getName(), e.getDescription(), e.getDate(), e.getTime(), e.getId(), e.getAgenda(), e.getStatus()))
                .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/savePoll/{id}")
    public void savePoll(PollDto pollDto, @PathParam("id") Long id) {
        List<AnswersDto> answersString = pollDto.getAnswers();
        //String[] answerList = answersString.split("\n");

        Poll poll = new Poll();
        poll.setQuestion(pollDto.getQuestion());
        poll.setEventID(id);
        poll.setIsFeedback(pollDto.isFeedback());

        List<Answer> answerList = new ArrayList<>();
        for (AnswersDto answersDto : answersString) {
            Answer answer = new Answer();
            answer.setPoll(poll);
            answer.setText(answersDto.getText());
            answerList.add(answer);
        }
        poll.setAnswers(answerList);
        em.persist(poll);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getPoll/{id}")
    public List<PollDto> getPollForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getPollForEvent(id);
        return poll.stream()
                .map(p -> new PollDto(p.getQuestion(),
                        toAnswersDtoList(p.getAnswers()),
                        p.isFeedback(),
                        p.getEventID(),
                        p.getId()))
                .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/vote/{id}")
    public void vote(@PathParam("id") Long id){
        Optional<Answer> answer=answersStore.getAnswerByID(id);
        if(answer.isPresent()){
            Answer a=answer.get();
            a.setCounter(a.getCounter()+1);
            System.out.print(a.getCounter());
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getVotes/{id}")
    public List<AnswersDto> getVotes(@PathParam("id") Long id){
        Poll poll=new Poll();
        poll.setId(id);
        List<Answer> answerList=answersStore.getAnswersByPollID(poll);
        System.out.print(answerList);
        return answerList.stream()
                .map(a->new AnswersDto(a.getId(),a.getCounter()))
                .collect(Collectors.toList());
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getFeedbackPoll/{id}")
    public List<PollDto> getFeedbackForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getFeedbackPoll(id);
        return poll.stream()
                .map(p -> new PollDto(p.getQuestion(),
                        toAnswersDtoList(p.getAnswers()),
                        p.isFeedback(),
                        p.getEventID(),
                        p.getId()))
                .collect(Collectors.toList());
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getVotingPoll/{id}")
    public List<PollDto> getVotingForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getVotingPoll(id);
        return poll.stream()
                .map(p -> new PollDto(p.getQuestion(),
                        toAnswersDtoList(p.getAnswers()),
                        p.isFeedback(),
                        p.getEventID(),
                        p.getId()))
                .collect(Collectors.toList());
    }

    private List<AnswersDto> toAnswersDtoList(List<Answer> list) {
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AnswersDto toDto(Answer answer) {
        AnswersDto r = new AnswersDto();
        r.setText(answer.getText());
        r.setThisAnswerID(answer.getId());
        return r;
    }

    @POST
    @Path("/delete/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    public void deleteEvent(@PathParam("id") Long id) throws IllegalArgumentException {
        eventStore.deleteEventById(id);
    }

    @POST
    @Path("/deletePoll/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    public void deletePoll(@PathParam("id") Long id) throws IllegalArgumentException {
        pollStore.deletePollById(id);
    }

}
