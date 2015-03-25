package com.tsystems.javaschool.dao.Impl;


import com.tsystems.javaschool.dao.RoleDAO;
import com.tsystems.javaschool.entities.Role;

import javax.persistence.EntityManager;
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {
    public RoleDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
