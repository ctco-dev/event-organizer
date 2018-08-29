package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.control.EventStore;
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
            return new EventDto(e.getName(), e.getDescription(), e.getDate(), e.getId(), e.getAgenda());
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
                .map(e -> new EventDto(e.getName(), e.getDescription(), e.getDate(), e.getId(), e.getAgenda()))
                .collect(Collectors.toList());
    }
}