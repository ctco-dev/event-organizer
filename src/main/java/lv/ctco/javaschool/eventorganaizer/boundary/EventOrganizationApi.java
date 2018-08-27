package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.*;
import sun.rmi.runtime.Log;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Path("/event")
@Stateless
public class EventOrganizationApi {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;

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
        User user = userStore.getCurrentUser();
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(user);
        em.persist(event);

    }

    @POST
    @Path("/update")
    @RolesAllowed({"USER", "ADMIN"})
    public void updateEvent(Event event) {
        User user = userStore.getCurrentUser();
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(user);
        em.merge(event);

    }


    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    public EventDto getEventById(@PathParam("id") Long id) throws IllegalArgumentException {
        Optional<Event> event = eventStore.getEventById(id);
        if (event.isPresent()) {
            Event e = event.get();
            return new EventDto(e);
        } else {
            throw new IllegalArgumentException();
        }
    }

    Event setFieldsToEvent(Event event, String adr, String value) throws IllegalArgumentException {
        switch (adr) {
            case ("name"):
                event.setName(value);
                break;
            case ("description"):
                event.setDescription(value);
                break;
            case ("date"):
                event.setDate(value);
                break;
            case ("id"):
                event.setId(Long.valueOf(value));
                break;
            default:
                throw new IllegalArgumentException();
        }
        return event;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getevents")
    public List<EventDto> getAllAuthorEvents() {
        User user = userStore.getCurrentUser();
        List<Event> event = eventStore.getAuthorEvents(user);
        List<EventDto> listE = new ArrayList<>();
        for (Event e : event) {
            EventDto eventDto = new EventDto(e);
            listE.add(eventDto);
        }
        return listE;
    }


}
