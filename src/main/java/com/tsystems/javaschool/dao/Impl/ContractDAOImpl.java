package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ContractDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ContractDAOImpl.
 */
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    public ContractDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }

    public Contract findContractByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        return contractTypedQuery.getResultList().get(0);
    }

    public List<Contract> findAllContracts() {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NOT NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    public List<Contract> findFreeNumbers() {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }
}
