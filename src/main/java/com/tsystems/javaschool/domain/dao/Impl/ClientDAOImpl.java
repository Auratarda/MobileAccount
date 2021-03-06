package com.tsystems.javaschool.domain.dao.Impl;

import com.tsystems.javaschool.domain.dao.ClientDAO;
import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class contains methods for the Client entity.
 * CRUD methods are extended from GenericDaoImpl.
 */
@Repository("ClientDAOImpl")
public class ClientDAOImpl extends GenericDaoImpl<Client> implements ClientDAO{

    /**
     * This method identifies client by email and password.
     */
    public Client login(String email, String password) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                em.createQuery("SELECT c FROM Client c WHERE c.email = :email " +
                        "AND c.password = :password", Client.class);
        clientTypedQuery.setParameter("email", email);
        clientTypedQuery.setParameter("password", password);
        List<Client> clients = clientTypedQuery.getResultList();
        if (clients.isEmpty()) throw new ClientNotFoundException();
        return clients.get(0);
    }

    /**
     * This method identifies client by first name, last name and email.
     */
    public Client identify(String firstName, String lastName, String email) throws ClientNotFoundException {
        TypedQuery<Client> clientTypedQuery =
                em.createQuery("SELECT c FROM Client c WHERE c.firstName = :firstName " +
                        "AND c.lastName = :lastName AND c.email = :email", Client.class);
        clientTypedQuery.setParameter("firstName", firstName);
        clientTypedQuery.setParameter("lastName", lastName);
        clientTypedQuery.setParameter("email", email);
        if (clientTypedQuery.getResultList().isEmpty()) throw new ClientNotFoundException();
        return clientTypedQuery.getResultList().get(0);
    }
}
