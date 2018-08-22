package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EventStore {
    @PersistenceContext
    private EntityManager em;

    public List<Event> getAllEvents() {
        return em.createQuery("select e from Event e" +
                " where e.status = :status", Event.class)
                .setParameter("status", EventStatus.OPEN)
                .getResultList();
    }


}
