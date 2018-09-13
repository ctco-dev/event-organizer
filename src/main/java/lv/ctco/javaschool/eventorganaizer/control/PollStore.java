package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;
import lv.ctco.javaschool.eventorganaizer.entity.UserPoll;

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

    public List<Poll> getPollForEvent(Long id) {
        return em.createQuery("select  p from Poll p" +
                " where p.eventID=:id", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }
    public int deletePollById(Long id)
    {
        return em.createQuery("delete from Event e where e.id=:id", Event.class)
                .setParameter("id", id)
                .executeUpdate();
    }
    public List<Poll> getVotingPoll(Long id) {
        return em.createQuery("select  p from Poll p" +
                " where p.eventID = :id and p.isFeedback = false", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Poll> getFeedbackPoll(Long id) {
        return em.createQuery("select p from Poll p" +
                " where p.eventID = :id and p.isFeedback = true", Poll.class)
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
    public List<Poll> getAllPolls(Long id){
        return em.createQuery("select p from Poll p" +
                " where p.eventID = :id", Poll.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Optional<UserPoll> getUserPollByUserAndPoll(User user, Poll poll) {
        return em.createQuery("select ua from UserPoll ua" +
                " where ua.user = :user and ua.poll = :poll", UserPoll.class)
                .setParameter("user", user)
                .setParameter("poll", poll)
                .getResultStream()
                .findFirst();
    }

    public List<UserPoll> getUserPollByUser(User user) {
        return em.createQuery("select ua from UserPoll ua" +
                " where ua.user = :user", UserPoll.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void persistUserPoll(UserPoll userPoll) {
        em.persist(userPoll);
    }

    public void persistPoll(Poll poll) {
        em.persist(poll);
    }
}
