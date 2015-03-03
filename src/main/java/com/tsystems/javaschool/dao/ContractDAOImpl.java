package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Contract;

import javax.persistence.EntityManager;

/**
 * ContractDAOImpl.
 */
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    private EntityManager em;
    public ContractDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.em = entityManager;
    }

    public EntityManager getEm() {
        return em;
    }
}
