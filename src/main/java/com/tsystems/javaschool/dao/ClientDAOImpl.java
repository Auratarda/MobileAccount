package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;

import javax.persistence.EntityManager;

/**
 * ClientDAOImpl.
 */
public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {
    public ClientDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
