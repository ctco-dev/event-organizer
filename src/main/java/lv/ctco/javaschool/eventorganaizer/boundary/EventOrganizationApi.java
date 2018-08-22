package lv.ctco.javaschool.eventorganaizer.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Map;

@Path("/event")
@Stateless
public class EventOrganizationApi {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserStore userStore;


    @POST
    @Path("/save")
    public void saveEvent(JsonObject jsonObject) {
        // EventDto eventDto = new EventDto();
        User user = userStore.getCurrentUser();
        Event event = new Event();
        for(Map.Entry<String,JsonValue> pair : jsonObject.entrySet()){
            String adr = pair.getKey();
            String value = ((JsonString) pair.getValue()).getString();
            if (adr == "name"){
                event.setName(value);
            }else if (adr == "description"){
                event.setDescription(value);
            }else if (adr == "datepicker"){
                event.setDate(value);
            }
        }
        event.setAuthor(user);
        em.persist(event);
    }


}
