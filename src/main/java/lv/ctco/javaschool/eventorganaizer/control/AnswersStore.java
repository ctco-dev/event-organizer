package lv.ctco.javaschool.eventorganaizer.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.eventorganaizer.entity.Answer;
import lv.ctco.javaschool.eventorganaizer.entity.Poll;
import lv.ctco.javaschool.eventorganaizer.entity.UserPoll;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class AnswersStore {
    @PersistenceContext
    private EntityManager em;

    public List<Answer> getAnswersByPollID(Poll poll) {
        return em.createQuery("select a from Answer a" +
                " where a.poll = :poll", Answer.class)
                .setParameter("poll", poll)
                .getResultList();
    }

    public Optional<Answer> getAnswerByID(Long id) {
        return em.createQuery("select a from Answer a" +
                " where a.id = :id", Answer.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
    public int deleteAnswerByPollId(Long id) {
        return em.createQuery("delete from Answer a where a.id in (select pool_id from Pool where pool_id=:id )", Answer.class)
                .setParameter("id", id)
                .executeUpdate();
    }
}
