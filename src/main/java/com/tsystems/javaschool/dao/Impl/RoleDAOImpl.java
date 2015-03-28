package com.tsystems.javaschool.dao.Impl;


import com.tsystems.javaschool.dao.RoleDAO;
import com.tsystems.javaschool.entities.Role;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository("RoleDAOImpl")
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {
    private EntityManager em;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    public void init() {
        this.setEntityManager(em);
        this.setEntityClass(Role.class);
    }
}
