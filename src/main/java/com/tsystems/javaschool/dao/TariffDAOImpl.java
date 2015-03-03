package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Tariff;

import javax.persistence.EntityManager;

/**
 * TariffDAOImpl.
 */
public class TariffDAOImpl extends GenericDAOImpl<Tariff, Long> implements TariffDAO {
    private EntityManager em;
    public TariffDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.em = entityManager;
    }

    public EntityManager getEm() {
        return em;
    }
}
