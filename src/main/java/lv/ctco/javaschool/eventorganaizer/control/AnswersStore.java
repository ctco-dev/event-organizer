package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AnswersStore {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private AnswersStore answersStore;

    public List<Answer> getAnswersByPollID(Long id) {
        return em.createQuery("select  a from Answer a" +
                " where a.pollID=:id", Answer.class)
                .setParameter("id", id)
                .getResultList();
    }

}
