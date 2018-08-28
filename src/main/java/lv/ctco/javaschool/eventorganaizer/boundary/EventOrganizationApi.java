package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.control.PollStore;
import lv.ctco.javaschool.eventorganaizer.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;

    @Inject
    private PollStore pollStore;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public TopicListDto getAllOpenEvents() {
        List<Event> listOfEvents = eventStore.getAllEvents();
        List<TopicDto> listTopics = new ArrayList<>();
        listOfEvents.forEach(e -> {
            TopicDto topicDto = new TopicDto(e);
            listTopics.add(topicDto);
        });
        return new TopicListDto(listTopics);
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
        event.setStatus(EventStatus.OPEN);
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
            return new EventDto(e.getName(),e.getDescription(),e.getDate(),e.getId());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getevents")
    public List<EventDto> getAllAuthorEvents() {
        List<Event> event = eventStore.getAuthorEvents(userStore.getCurrentUser());

      return event.stream()
              .map(e -> new EventDto(e.getName(),e.getDescription(),e.getDate(),e.getId()))
              .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/savePoll/{id}")
    public void savePoll(Poll poll,@PathParam("id") Long id){
       poll.setEventID(id);
       em.persist(poll);

    }
//
//    @GET
//    @RolesAllowed({"ADMIN", "USER"})
//    @Path("/getPoll/{id}")
//    public List<PollDto> getPollForEvent(){
//        List<Poll> pollList=pollStore.getPollByIdEvent();
//
//    }




}
