package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.TariffDAO;
import com.tsystems.javaschool.entities.Tariff;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 * TariffDAOImpl.
 */
@Repository("TariffDAOImpl")
public class TariffDAOImpl extends GenericDAOImpl<Tariff, Long> implements TariffDAO {
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.setEntityManager(em);
        this.setEntityClass(Tariff.class);
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Tariff findTariffByName(String tariffName) {
        TypedQuery<Tariff> tariffTypedQuery = getEntityManager().createQuery
                ("SELECT t FROM Tariff t WHERE t.name = :tariffName", Tariff.class);
        tariffTypedQuery.setParameter("tariffName", tariffName);
        return tariffTypedQuery.getResultList().get(0);
    }
}
