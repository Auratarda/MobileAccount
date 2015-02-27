package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Tariff;

import javax.persistence.EntityManager;

/**
 * TariffDAOImpl.
 */
public class TariffDAOImpl extends GenericDAOImpl<Tariff, Long> implements TariffDAO {
    public TariffDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
