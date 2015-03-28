package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.OptionDAO;
import com.tsystems.javaschool.entities.Option;
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
 * OptionDAOImpl.
 */
@Repository("OptionDAOImpl")
public class OptionDAOImpl implements OptionDAO {

    @PersistenceContext(unitName = "MobileAccountPU",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    @Autowired
    @Qualifier("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean fb;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Option findOptionByName(String optionName) {
        TypedQuery<Option> optionTypedQuery = em.createQuery
                ("SELECT o FROM Option o WHERE o.name = :optionName", Option.class);
        optionTypedQuery.setParameter("optionName", optionName);
        return optionTypedQuery.getResultList().get(0);
    }

    @Override
    public Option create(Option t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.persist(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    @Transactional
    public Option read(Long id) {
        return em.find(Option.class, id);
    }

    @Override
    public Option update(Option t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.merge(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public void delete(Option t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.remove(t);
        entMan.getTransaction().commit();
        entMan.close();
    }

    @Override
    @Transactional
    public List<Option> getAll() {
        TypedQuery<Option> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Option c", Option.class);
        return clientTypedQuery.getResultList();
    }
}
