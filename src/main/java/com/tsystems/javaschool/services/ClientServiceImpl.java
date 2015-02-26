package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.dao.ClientDAOImpl;
import com.tsystems.javaschool.entities.Client;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * ClientServiceImpl.
 */
public class ClientServiceImpl {
    //TODO: why do you need no access modifier?
    final static Logger logger = Logger.getLogger(ClientServiceImpl.class);
    ClientDAO clientDAO;

    public ClientServiceImpl(EntityManager entityManager) {
        clientDAO = new ClientDAOImpl(entityManager);
    }

    public void saveNewClient(Client client) {
        try {
            clientDAO.create(client);
            logger.info("Persist success!");
        } catch (Exception e) {
            logger.error("Persist fail!", e);
        }
    }
}
