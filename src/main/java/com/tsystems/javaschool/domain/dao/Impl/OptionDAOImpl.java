package com.tsystems.javaschool.domain.dao.Impl;

import com.tsystems.javaschool.domain.dao.OptionDAO;
import com.tsystems.javaschool.domain.entities.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * OptionDAOImpl.
 */
@Repository("OptionDAOImpl")
public class OptionDAOImpl extends GenericDaoImpl<Option> implements OptionDAO {

    public Option findOptionByName(String optionName) {
        TypedQuery<Option> optionTypedQuery = em.createQuery
                ("SELECT o FROM Option o WHERE o.name = :optionName", Option.class);
        optionTypedQuery.setParameter("optionName", optionName);
        return optionTypedQuery.getResultList().get(0);
    }
}