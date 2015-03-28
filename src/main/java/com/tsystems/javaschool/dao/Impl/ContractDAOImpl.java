package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ContractDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ContractDAOImpl.
 */
@Repository("ContractDAOImpl")
public class ContractDAOImpl extends GenericDAOImpl<Contract, Long> implements ContractDAO {
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.setEntityManager(em);
        this.setEntityClass(Contract.class);
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }

    @Transactional
    public Contract findContractByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        return contractTypedQuery.getResultList().get(0);
    }

    @Transactional
    public List<Contract> findAllContracts() {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NOT NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    @Transactional
    public List<Contract> findFreeNumbers() {
        TypedQuery<Contract> contractTypedQuery = getEntityManager().createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }
}
