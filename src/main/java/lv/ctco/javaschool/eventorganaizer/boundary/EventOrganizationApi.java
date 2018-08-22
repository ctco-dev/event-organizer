package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.eventorganaizer.control.EventStore;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.TopicDto;
import lv.ctco.javaschool.eventorganaizer.entity.TopicListDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("/event")
@Stateless
public class EventOrganizationApi {
    @PersistenceContext
    private EntityManager em;

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


}
