package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.OptionDAO;
import com.tsystems.javaschool.entities.Option;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * OptionDAOImpl.
 */
public class OptionDAOImpl extends GenericDAOImpl<Option, Long> implements OptionDAO {
    public OptionDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Option findOptionByName(String optionName) {
        TypedQuery<Option> optionTypedQuery = getEntityManager().createQuery
                ("SELECT o FROM Option o WHERE o.name = :optionName", Option.class);
        optionTypedQuery.setParameter("optionName", optionName);
        return optionTypedQuery.getResultList().get(0);
    }
}
