package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;

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
    @RolesAllowed({"USER", "ADMIN"})
    public void saveEvent(JsonObject jsonObject) {
        User user = userStore.getCurrentUser();
        Event event = new Event();
        for(Map.Entry<String,JsonValue> pair : jsonObject.entrySet()){
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            event = setFieldsToEvent(event, adr, value);
        }
        event.setStatus(EventStatus.OPEN);
        event.setAuthor(user);
        em.persist(event);
    }

    Event setFieldsToEvent(Event event, String adr, String value) throws IllegalArgumentException {
        switch (adr){
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


}
