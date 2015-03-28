package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.OptionDAO;
import com.tsystems.javaschool.entities.Option;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 * OptionDAOImpl.
 */
@Repository("OptionDAOImpl")
public class OptionDAOImpl extends GenericDAOImpl<Option, Long> implements OptionDAO {
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.setEntityManager(em);
        this.setEntityClass(Option.class);
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Option findOptionByName(String optionName) {
        TypedQuery<Option> optionTypedQuery = getEntityManager().createQuery
                ("SELECT o FROM Option o WHERE o.name = :optionName", Option.class);
        optionTypedQuery.setParameter("optionName", optionName);
        return optionTypedQuery.getResultList().get(0);
    }
}
