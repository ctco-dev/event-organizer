package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.EventStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Stateless
public class EventStore {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserStore userStore;

    public List<Event> getAllEvents() {
        List<Event> list;
        list = em.createQuery("select e from Event e" +
                " where e.status = :status", Event.class)
                .setParameter("status", EventStatus.OPEN)
                .getResultList();
        return list;
    }

    public Optional<Event> getEventById(Long id) {
        return em.createQuery("select  e from Event e" +
                " where e.id = :id", Event.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
    public List<Event> getAuthorEvents(User user) {
        return em.createQuery("select e from Event e where e.author=:user", Event.class)
                .setParameter("user", user)
                .getResultList();

    }
}
