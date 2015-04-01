package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ContractDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ContractDAOImpl.
 */
@Repository("ContractDAOImpl")
public class ContractDAOImpl extends GenericDaoImpl<Contract> implements ContractDAO {

    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }

    public Contract findContractByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        return contractTypedQuery.getResultList().get(0);
    }

    public List<Contract> findAllContracts() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NOT NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    public List<Contract> findFreeNumbers() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }
}
