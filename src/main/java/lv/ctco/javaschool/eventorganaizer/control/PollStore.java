package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class PollStore {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private PollStore pollStore;

    public Optional<Poll> getPollByIdEvent(Long id) {
        return em.createQuery("select  p from Poll p" +
                " where p.eventID=:id", Poll.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }


}
