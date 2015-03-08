package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Tariff;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * TariffDAOImpl.
 */
public class TariffDAOImpl extends GenericDAOImpl<Tariff, Long> implements TariffDAO {
    public TariffDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Tariff findTariffByName(String tariffName) {
        TypedQuery<Tariff> tariffTypedQuery = getEntityManager().createQuery
                ("SELECT t FROM Tariff t WHERE t.name = :tariffName", Tariff.class);
        tariffTypedQuery.setParameter("tariffName", tariffName);
        return tariffTypedQuery.getResultList().get(0);
    }
}
