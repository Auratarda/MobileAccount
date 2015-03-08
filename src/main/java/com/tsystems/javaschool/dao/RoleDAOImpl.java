package com.tsystems.javaschool.dao;


import com.tsystems.javaschool.entities.Role;

import javax.persistence.EntityManager;

public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {
    public RoleDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
