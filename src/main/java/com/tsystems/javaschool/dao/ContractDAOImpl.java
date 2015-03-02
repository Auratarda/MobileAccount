package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Contract;

import javax.persistence.EntityManager;

/**
 * ContractDAOImpl.
 */
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    public ContractDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
