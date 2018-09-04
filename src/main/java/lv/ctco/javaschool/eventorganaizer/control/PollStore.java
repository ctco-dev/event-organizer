package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Poll;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class PollStore {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private PollStore pollStore;

    public List<Poll> getPollForEvent(Long id) {
        return em.createQuery("select  p from Poll p" +
                " where p.eventID=:id", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }

    public int deletePollById(Long id) {
        return em.createQuery("delete from Poll p where p.id=:id", Poll.class)
                .setParameter("id", id)
                .executeUpdate();

    }

    public List<Poll> getVotingPoll(Long id) {
        return em.createQuery("select  p from Poll p" +
                " where p.eventID=:id and p.isFeedback=false", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Poll> getFeedbackPoll(Long id) {
        return em.createQuery("select p from Poll p" +
                " where p.eventID=:id and p.isFeedback=true", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Optional<Poll> getPollById (Long id) {
        return em.createQuery("select p from Poll p" +
                " where p.id = :id", Poll.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
