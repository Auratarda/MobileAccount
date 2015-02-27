package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Option;

import javax.persistence.EntityManager;

/**
 * OptionDAOImpl.
 */
public class OptionDAOImpl extends GenericDAOImpl<Option, Long> implements OptionDAO {
    public OptionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
