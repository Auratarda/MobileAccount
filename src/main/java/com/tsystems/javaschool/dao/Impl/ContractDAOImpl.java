package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ContractDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ContractDAOImpl.
 */
@Repository("ContractDAOImpl")
public class ContractDAOImpl implements ContractDAO {

    @PersistenceContext(unitName = "MobileAccountPU",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    @Autowired
    @Qualifier("entityManagerFactory")
    LocalContainerEntityManagerFactoryBean fb;
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Client findClientByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        Contract contract = contractTypedQuery.getResultList().get(0);
        return contract.getClient();
    }

    @Transactional
    public Contract findContractByNumber(String number) {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.number = :number", Contract.class);
        contractTypedQuery.setParameter("number", number);
        return contractTypedQuery.getResultList().get(0);
    }

    @Transactional
    public List<Contract> findAllContracts() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NOT NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    @Transactional
    public List<Contract> findFreeNumbers() {
        TypedQuery<Contract> contractTypedQuery = em.createQuery
                ("SELECT c FROM Contract c WHERE c.client IS NULL", Contract.class);
        return contractTypedQuery.getResultList();
    }

    @Override
    public Contract create(Contract t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.persist(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    @Transactional
    public Contract read(Long id) {
        return em.find(Contract.class, id);
    }

    @Override
    public Contract update(Contract t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.merge(t);
        entMan.getTransaction().commit();
        entMan.close();
        return t;
    }

    @Override
    public void delete(Contract t) {
        EntityManager entMan = fb.getObject().createEntityManager();
        entMan.getTransaction().begin();
        entMan.remove(t);
        entMan.getTransaction().commit();
        entMan.close();
    }

    @Override
    @Transactional
    public List<Contract> getAll() {
        TypedQuery<Contract> clientTypedQuery =
                getEm().createQuery("SELECT c FROM Contract c", Contract.class);
        return clientTypedQuery.getResultList();
    }
}
