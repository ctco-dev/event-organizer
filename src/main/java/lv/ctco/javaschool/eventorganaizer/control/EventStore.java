package lv.ctco.javaschool.eventorganaizer.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EventStore {
    @PersistenceContext
    private EntityManager em;


}
