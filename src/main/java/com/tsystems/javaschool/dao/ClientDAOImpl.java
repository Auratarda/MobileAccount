package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Client;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * ClientDAOImpl.
 */
public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {
    private EntityManager em;
    public ClientDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.em = entityManager;
    }

    // TODO: not used
    public EntityManager getEm() {
        return em;
    }

    public Client login(String email, String password) {
        // TODO: why do you need getEntityManager() ?
        TypedQuery<Client> client =
                em.createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        client.setParameter("email", email);
        client.setParameter("password", password);
        return client.getSingleResult();
    }
}
