package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.dao.ClientDAOImpl;
import com.tsystems.javaschool.entities.Client;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * ClientServiceImpl.
 */
public class ClientServiceImpl implements ClientService {
    final static Logger logger = Logger.getLogger(ClientServiceImpl.class);
    ClientDAO clientDAO;

    public ClientServiceImpl(EntityManager entityManager) {
        clientDAO = new ClientDAOImpl(entityManager);
    }

    public void createClient(Client client) {
        try {
            clientDAO.create(client);
            logger.info("Persist success!");
        } catch (Exception e) {
            logger.error("Persist fail!", e);
        }
    }

    public Client readClient(Long id) {
        try {
            Client client = clientDAO.read(id);
            logger.info("Find success!");
            return client;
        } catch (Exception e) {
            logger.error("Find fail!", e);
        }
        return null;
    }

    public void updateClient(Client client) {
        try {
            clientDAO.update(client);
            logger.info("Merge success!");
        } catch (Exception e) {
            logger.error("Merge fail!", e);
        }
    }

    public void deleteClient(Client client) {
        try {
            clientDAO.delete(client);
            logger.info("Remove success!");
        } catch (Exception e) {
            logger.error("Remove fail!", e);
        }
    }
}
