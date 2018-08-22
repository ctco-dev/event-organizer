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
            TopicDto topicDto = new TopicDto(e.getName(), e.getAuthor().getUsername(),
                    e.getDate(), false);
            listTopics.add(topicDto);
        });
        return new TopicListDto(listTopics);
    }

    @POST
    @Path("/save")
    public void saveEvent(JsonObject jsonObject) {
        // EventDto eventDto = new EventDto();
        User user = userStore.getCurrentUser();
        Event event = new Event();
        for (Map.Entry<String, JsonValue> pair : jsonObject.entrySet()) {
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr.equals("name")) {
                event.setName(value);
            } else if (adr.equals("description")) {
                event.setDescription(value);
            } else if (adr.equals("datepicker")) {
                event.setDate(value);
            }
        }
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(user);
        em.persist(event);
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/getMyEvents")
    public List<EventDto> getAllAuthorEvents() {
        User user = userStore.getCurrentUser();
        List<Event> event = eventStore.getAuthorEvents(user);
        return event.stream()
                .map(this::convertToEventDto)
                .collect(Collectors.toList());
    }

    public EventDto convertToEventDto(Event event) {
        EventDto dto = new EventDto();
        dto.setEventName(event.getName());
        dto.setEventDescription(event.getDescription());
        dto.getEventDate(event.getDate());
        return dto;
    }
}
