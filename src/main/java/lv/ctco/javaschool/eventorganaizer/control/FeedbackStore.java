package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.Event;
import lv.ctco.javaschool.eventorganaizer.entity.Feedback;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FeedbackStore {
    @PersistenceContext
    private EntityManager em;

    public List<Feedback> getFeedbackForEvent(Event event) {
        return em.createQuery("select p from Feedback p" +
                " where p.event=:event", Feedback.class)
                .setParameter("event", event)
                .getResultList();
    }

    public void persistFeedback(Feedback feedback) {
        em.persist(feedback);
    }
}