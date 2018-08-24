package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.*;

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
    public void saveEvent(JsonObject jsonObject) {
        User user = userStore.getCurrentUser();
        Event event = new Event();
        for (Map.Entry<String, JsonValue> pair : jsonObject.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            event = setFieldsToEvent(event, adr, value);
        }
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(user);
        if(event.getId()==null) {
            em.persist(event);
        }
        else {
            em.merge(event);
        }
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    public EventDto getEventById(@PathParam("id") Long id) {
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
            case ("datepicker"):
                event.setDate(value);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return event;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getevents")
    public EventListDto getAllAuthorEvents() {
        User user = userStore.getCurrentUser();
        List<Event> event = eventStore.getAuthorEvents(user);

        return new EventListDto(event.stream()
                .map(this::convertToEventDto)
                .collect(Collectors.toList()));
    }

    public EventDto convertToEventDto(Event event) {
        EventDto dto = new EventDto();
        dto.setEventName(event.getName());
        dto.setEventDescription(event.getDescription());
        dto.setEventDate(event.getDate());
        dto.setEventID(event.getId());
        return dto;
    }


    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getoneevent/{id}")
    public EventDto getEvent(@PathParam("id") Long id) {
        Optional<Event> event1 = eventStore.getEventById(id);

        if (event1.isPresent()) {
            Event e = event1.get();
            EventDto dto = convertToEventDto(e);
            return dto;
        } else
            return null;
    }

}
