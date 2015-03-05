package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }
}
