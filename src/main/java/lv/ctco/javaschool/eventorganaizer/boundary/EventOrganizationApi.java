package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.eventorganaizer.control.AnswersStore;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.control.PollStore;
import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.AnswerDto;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventDto;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;
import lv.ctco.javaschool.eventorganaizer.entity.PollDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;

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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/event")
@Stateless
public class EventOrganizationApi {
    @PersistenceContext
    private EntityManager em;
//
    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;

    @Inject
    private PollStore pollStore;

    @Inject
    private AnswersStore answersStore;

    @Inject
    private Mapper mapper;

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
        eventStore.persistEvent(event);
    }

    @POST
    @Path("/update")
    @RolesAllowed({"USER", "ADMIN"})
    public void updateEvent(Event event) {
        event.setAuthor(userStore.getCurrentUser());
        eventStore.mergeEvent(event);
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
    @Path("/getEvents")
    public List<EventDto> getAllAuthorEvents() {
        List<Event> event = eventStore.getAuthorEvents(userStore.getCurrentUser());
        return event.stream()
                .map(e -> new EventDto(e.getName(), e.getDescription(), e.getDate(), e.getTime(), e.getId(), e.getAgenda(), e.getStatus()))
                .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/savePoll/")
    public void savePoll(PollDto pollDto, @PathParam("id") Long id) {
        Poll poll = new Poll();
        poll.setQuestion(pollDto.getQuestion());
        poll.setEventID(id);
        poll.setIsFeedback(pollDto.isFeedback());
        poll.setAnswers(mapper.mapPollToAnswerList(poll, pollDto));
        pollStore.persistPoll(poll);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getPoll")
    public List<PollDto> getPollForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getPollForEvent(id);
        return mapper.mapPollToDto(poll);
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/vote")
    public void updateVoteCounter(@PathParam("id") Long id) {
        Optional<Answer> answer = answersStore.getAnswerByID(id);
        if (answer.isPresent()) {
            answer.get().setCounter(answer.get().getCounter() + 1);
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getVotes")
    public List<AnswerDto> getVotes(@PathParam("id") Long id) {
        Optional<Poll> poll = pollStore.getPollById(id);
        List<AnswerDto> answerDtos = new ArrayList<>();
        poll.ifPresent(p -> {
            List<Answer> answerList = answersStore.getAnswersByPollID(p);
            mapper.mapAnswerToAnswerDto(answerList);
        });
        return answerDtos;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getFeedbackPoll")
    public List<PollDto> getFeedbackForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getFeedbackPoll(id);
        return mapper.mapPollToDto(poll);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getVotingPoll")
    public List<PollDto> getVotingForEvent(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getVotingPoll(id);
        return mapper.mapPollToDto(poll);
    }

    @POST
    @Path("/{id}/delete")
    @RolesAllowed({"USER", "ADMIN"})
    public void deleteEvent(@PathParam("id") Long id) throws IllegalArgumentException {
        eventStore.deleteEventById(id);
    }

    @POST
    @Path("/{id}/deletePoll")
    @RolesAllowed({"USER", "ADMIN"})
    public void deletePoll(@PathParam("id") Long id) throws IllegalArgumentException {
        pollStore.deletePollById(id);
    }
}
