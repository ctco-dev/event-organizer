package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.AnswersStore;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.control.FeedbackStore;
import lv.ctco.javaschool.eventorganaizer.control.PollStore;
import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.AnswerDto;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventDto;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;
import lv.ctco.javaschool.eventorganaizer.entity.Feedback;
import lv.ctco.javaschool.eventorganaizer.entity.FeedbackDto;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;
import lv.ctco.javaschool.eventorganaizer.entity.PollDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;
import lv.ctco.javaschool.eventorganaizer.entity.UserPoll;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
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
    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;

    @Inject
    private PollStore pollStore;

    @Inject
    private AnswersStore answersStore;

    @Inject
    private FeedbackStore feedbackStore;

    @Inject
    private Mapper mapper;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/getNotEmptyEvents")
    public  TopicListDto getAllNotEmptyEvents() {
        List<TopicDto> topicDtos =new ArrayList<>();
        List<Event> allEvents =eventStore.getAllEvents();;
        allEvents.forEach(a->{
            List<Poll> poll = pollStore.getPollForEvent(a.getId());
            if(poll.size()>0)
            {
                topicDtos.add(mapper.eventToTopicDto(a));
            }
        });
        return new TopicListDto(topicDtos);
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
    @Path("/vote/{id}")
    public void updateVoteCounter(@PathParam("id") Long id) {
        User currentUser = userStore.getCurrentUser();
        Optional<Answer> userAnswer = answersStore.getAnswerByID(id);
        if (userAnswer.isPresent()) {
            Answer answer = userAnswer.get();
            Optional<UserPoll> userVote = pollStore.getUserPollByUserAndPoll(currentUser, answer.getPoll());
            if (userVote.isPresent()) {
                throw new EntityNotFoundException();
            }
            registerNewUserPoll(currentUser, answer);
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getAnswers")
    public List<PollDto> getPollsByEventId() {
        User currentUser = userStore.getCurrentUser();
        List<UserPoll> userPollList = pollStore.getUserPollByUser(currentUser);
        List<PollDto> pollDtos = new ArrayList<>();
        userPollList.forEach(up -> {
            PollDto pollDto = new PollDto();
            pollDto.setId(up.getPoll().getId());
            pollDtos.add(pollDto);
        });
        return pollDtos;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getPolls")
    public List<PollDto> getAllPolls(@PathParam("id") Long id) {
        List<Poll> poll = pollStore.getAllPolls(id);
        return mapper.mapPollToDto(poll);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getVotes")
    public List<AnswerDto> getVotes(@PathParam("id") Long id) {
        Optional<Poll> poll = pollStore.getPollById(id);
        List<AnswerDto> answerDtos = new ArrayList<>();
        poll.ifPresent(p -> {
            List<Answer> answerList = answersStore.getAnswersByPollID(p);
            mapper.mapAnswerToAnswerDto(answerList).forEach(a -> {
                answerDtos.add(a);
            });
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
        List<PollDto> pollDtos = mapper.mapPollToDto(poll);
        return pollDtos;
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
       // answersStore.deleteAnswerByPollId(id);
        pollStore.deletePollById(id);
    }

    private void registerNewUserPoll(User currentUser, Answer answer) {
        UserPoll newUserPoll = new UserPoll();
        newUserPoll.setUser(currentUser);
        newUserPoll.setPoll(answer.getPoll());
        pollStore.persistUserPoll(newUserPoll);
        answer.setCounter(answer.getCounter() + 1);
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/saveFeedback")
    public void saveFeedback(String feedbackText, @PathParam("id") Long id) {
        Feedback feedback = new Feedback();
        feedback.setEvent(eventStore.getEventById(id).get());
        feedback.setFeedbackAuthor(userStore.getCurrentUser().getUsername());
        feedbackText = feedbackText.replace('"', ' ');
        feedback.setFeedbackText(feedbackText);
        feedbackStore.persistFeedback(feedback);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}/getFeedbackText")
    public List<FeedbackDto> getTextFeedback(@PathParam("id") Long id) {
        List<Feedback> feedbacks = feedbackStore.getFeedbackForEvent(eventStore.getEventById(id).get());
        return mapper.mapFeedbackToFeedbackDto(feedbacks);
    }
}
