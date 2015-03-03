package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * ClientServiceImpl.
 */
public class ClientServiceImpl implements ClientService {
    //TODO: why do you need no access modifier?
    // TODO: Done
    private final static Logger logger = Logger.getLogger(ClientServiceImpl.class);
    private EntityManager em;

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;
    private TariffDAO tariffDAO;
    private OptionDAO optionDAO;

    public ClientServiceImpl(EntityManager em) {
        logger.info("Creating client service");
        this.em = em;
        clientDAO = new ClientDAOImpl(em);
        contractDAO = new ContractDAOImpl(em);
        tariffDAO = new TariffDAOImpl(em);
        optionDAO = new OptionDAOImpl(em);
    }

    public EntityManager getEm() {
        return em;
    }

    /**
     * View contract.
     */
    public Client login(String email, String password) {
        logger.debug("Attempting to log in");
        return clientDAO.login(email, password);
    }

    public List<Contract> viewContracts(Client client) {
        return null;
    }

    public void changeTariff(String tariffId) {

    }

    public void addOption(String optionId) {

    }

    public void removeOption(String optionId) {

    }

    public void lockContract(String contractId) {

    }
}
