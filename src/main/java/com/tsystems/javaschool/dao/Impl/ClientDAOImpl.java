package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.exceptions.LoginException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * ClientDAOImpl.
 */

public class ClientDAOImpl extends GenericDAOImpl<Client, Long> implements ClientDAO {
    public ClientDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Client login(String email, String password) throws LoginException {
        TypedQuery<Client> clientTypedQuery =
                getEntityManager().createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        clientTypedQuery.setParameter("email", email);
        clientTypedQuery.setParameter("password", password);
        if (clientTypedQuery.getResultList().isEmpty()) throw new LoginException();
        return clientTypedQuery.getResultList().get(0);
    }
}
