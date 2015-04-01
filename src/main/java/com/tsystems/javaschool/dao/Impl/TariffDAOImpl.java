package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.TariffDAO;
import com.tsystems.javaschool.entities.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * TariffDAOImpl.
 */
@Repository("TariffDAOImpl")
public class TariffDAOImpl extends GenericDaoImpl<Tariff> implements TariffDAO {

    public Tariff findTariffByName(String tariffName) {
        TypedQuery<Tariff> tariffTypedQuery = em.createQuery
                ("SELECT t FROM Tariff t WHERE t.name = :tariffName", Tariff.class);
        tariffTypedQuery.setParameter("tariffName", tariffName);
        return tariffTypedQuery.getResultList().get(0);
    }
}
