package com.tsystems.javaschool.dao.Impl;


import com.tsystems.javaschool.dao.RoleDAO;
import com.tsystems.javaschool.entities.Role;
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

@Repository("RoleDAOImpl")
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext(unitName = "MobileAccountPU",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    @Autowired
    @Qualifier("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean fb;
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Role create(Role t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.persist(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public Role read(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role update(Role t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.merge(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public void delete(Role t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.remove(t);
        entMan.getTransaction().commit();
        entMan.close();
    }

    @Override
    @Transactional
    public List<Role> getAll() {
        TypedQuery<Role> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Role c", Role.class);
        return clientTypedQuery.getResultList();
    }
}
