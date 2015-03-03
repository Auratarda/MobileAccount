package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Option;

import javax.persistence.EntityManager;

/**
 * OptionDAOImpl.
 */
public class OptionDAOImpl extends GenericDAOImpl<Option, Long> implements OptionDAO {
    private EntityManager em;
    public OptionDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.em = entityManager;
    }

    public EntityManager getEm() {
        return em;
    }
}
