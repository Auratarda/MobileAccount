package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * ClientDAOImpl.
 */
@Repository("ClientDAOImpl")
public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {

    private EntityManager em;

    @PostConstruct
    public void init() {
        this.setEntityManager(em);
        this.setEntityClass(Client.class);
    }

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Client login(String email, String password) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                getEntityManager().createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        clientTypedQuery.setParameter("email", email);
        clientTypedQuery.setParameter("password", password);
        List<Client> clients = clientTypedQuery.getResultList();
        if (clients.isEmpty()) throw new ClientNotFoundException();
        return clients.get(0);
    }

    @Transactional
    public Client identify(String firstName, String lastName, String email) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                getEntityManager().createQuery("SELECT c FROM Client c WHERE c.firstName = :firstName " +
                        "AND c.lastName = :lastName AND c.email = :email", Client.class);
        clientTypedQuery.setParameter("firstName", firstName);
        clientTypedQuery.setParameter("lastName", lastName);
        clientTypedQuery.setParameter("email", email);
        if (clientTypedQuery.getResultList().isEmpty()) throw new ClientNotFoundException();
        return clientTypedQuery.getResultList().get(0);
    }
}
