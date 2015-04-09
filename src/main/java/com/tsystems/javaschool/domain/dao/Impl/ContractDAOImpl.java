package com.tsystems.javaschool.domain.dao.Impl;

import com.tsystems.javaschool.domain.dao.ContractDAO;
import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.domain.entities.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class contains methods for the Contract entity.
 * CRUD methods are extended from GenericDaoImpl.
 */
@Repository("ContractDAOImpl")
public class ContractDAOImpl extends GenericDaoImpl<Contract> implements ContractDAO {

    /**
     * This method identifies client by number.
     */
    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }

    /**
     * This method identifies contract by number.
     */
    public Contract findContractByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        return contractTypedQuery.getResultList().get(0);
    }

    /**
     * This method retrieves all contracts assigned to clients.
     */
    public List<Contract> findAllContracts() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NOT NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    /**
     * This method retrieves all unassigned contracts.
     */
    public List<Contract> findFreeNumbers() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    /**
     * This method retrieves all contracts assigned to a particular tariff.
     */
    public List<Contract> findContractsByTariff(String tariffName){
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.tariff.name = :tariffName", Contract.class)
                .setParameter("tariffName", tariffName);
        return contractTypedQuery.getResultList();
    }
}
