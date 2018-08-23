package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class EventStore {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserStore userStore;

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        list = em.createQuery("select e from Event e" +
                " where e.status = :status", Event.class)
                .setParameter("status", EventStatus.OPEN)
                .getResultList();
        return list;
    }

    public List<Event> getAuthorEvents(User user){
        return em.createQuery("select e from Event e where e.author=:user",Event.class)
                .setParameter("user",user)
                .getResultList();

    }

    public Event getEventById(){
        return  em.createQuery("select e from Event where e.id=:id",Event.class)
                .setParameter("id",);
    }

}
