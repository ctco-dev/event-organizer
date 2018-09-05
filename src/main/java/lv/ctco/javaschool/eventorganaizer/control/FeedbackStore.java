package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Feedback;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

    @Stateless
    public class FeedbackStore {

        @PersistenceContext
        private EntityManager em;

        @Inject
        private FeedbackStore feedbackStore;

        public List<Feedback> getFeedbackForEvent(Long id) {
            return em.createQuery("select  p from Feedback p" +
                    " where p.eventID=:id", Feedback.class)
                    .setParameter("id", id)
                    .getResultList();
        }

        public void persistFeedback(Feedback feedback) {
            em.persist(feedback);
        }
    }

