package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.TariffDAO;
import com.tsystems.javaschool.entities.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * TariffDAOImpl.
 */
@Repository("TariffDAOImpl")
public class TariffDAOImpl implements TariffDAO {

    @PersistenceContext(unitName = "MobileAccountPU",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Autowired
    @Qualifier("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean fb;

    @Transactional
    public Tariff findTariffByName(String tariffName) {
        TypedQuery<Tariff> tariffTypedQuery = em.createQuery
                ("SELECT t FROM Tariff t WHERE t.name = :tariffName", Tariff.class);
        tariffTypedQuery.setParameter("tariffName", tariffName);
        return tariffTypedQuery.getResultList().get(0);
    }

    @Override
    public Tariff create(Tariff t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.persist(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    public EntityManager getEm() {
        return em;
    }

    @Override
    @Transactional
    public Tariff read(Long id) {
        return em.find(Tariff.class, id);
    }

    @Override
    public Tariff update(Tariff t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.merge(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public void delete(Tariff t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.remove(t);
        entMan.getTransaction().commit();
        entMan.close();
    }

    @Override
    @Transactional
    public List<Tariff> getAll() {
        TypedQuery<Tariff> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Tariff c", Tariff.class);
        return clientTypedQuery.getResultList();
    }
}
