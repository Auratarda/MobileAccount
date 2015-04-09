package com.tsystems.javaschool.domain.dao.Impl;

import com.tsystems.javaschool.domain.dao.OptionDAO;
import com.tsystems.javaschool.domain.entities.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * This class contains methods for the Option entity.
 * CRUD methods are extended from GenericDaoImpl.
 */
@Repository("OptionDAOImpl")
public class OptionDAOImpl extends GenericDaoImpl<Option> implements OptionDAO {

    /**
     * This method identifies option by name.
     */
    public Option findOptionByName(String optionName) {
        TypedQuery<Option> optionTypedQuery = em.createQuery
                ("SELECT o FROM Option o WHERE o.name = :optionName", Option.class);
        optionTypedQuery.setParameter("optionName", optionName);
        return optionTypedQuery.getResultList().get(0);
    }

    public void removeRequiredOptions(String optionName) {
        Option option = findOptionByName(optionName);
        Long id = option.getOptionId();

        TypedQuery<Option> optionTypedQuery = em.createQuery
                ("DELETE FROM Option o WHERE o.optionId = :id", Option.class);
        optionTypedQuery.setParameter("id", id);
        int deleted = optionTypedQuery.executeUpdate();
    }
}
