package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.LoginException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * ClientServiceImpl.
 */
public class ClientServiceImpl implements ClientService {
    private final static Logger logger = Logger.getLogger(ClientServiceImpl.class);

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;
    private TariffDAO tariffDAO;
    private OptionDAO optionDAO;

    public ClientServiceImpl(EntityManager em) {
        logger.info("Creating client service");
        clientDAO = new ClientDAOImpl(em);
        contractDAO = new ContractDAOImpl(em);
        tariffDAO = new TariffDAOImpl(em);
        optionDAO = new OptionDAOImpl(em);
    }

    /**
     * View contract.
     */
    public Client login(String email, String password) throws LoginException {
        return clientDAO.login(email, password);
    }

    public Set<Contract> viewContracts(String clientId) {
        Long id = Long.parseLong(clientId);
        Client client = clientDAO.read(id);
        return client.getContracts();
    }

    // TODO: what will happen with options?
    // TODO: locked check
    public void changeTariff(String contractId, String tariffId) {
        Long conId = Long.parseLong(contractId);
        Long tarId = Long.parseLong(tariffId);
        Contract contract = contractDAO.read(conId);
        Tariff tariff = tariffDAO.read(tarId);
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    // TODO: how will you check options compatibility?
    // TODO: locked check
    public void addOption(String contractId, String optionId) {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        Option option = optionDAO.read(optId);
        contract.getOptions().add(option);
        contractDAO.update(contract);
    }

    // TODO: locked check
    public void removeOption(String contractId, String optionId) {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        Option option = optionDAO.read(optId);
        contract.getOptions().remove(option);
        contractDAO.update(contract);
    }

    public void lockContract(String contractId) {
        Long conId = Long.parseLong(contractId);
        Contract contract = contractDAO.read(conId);
        contract.setBlockedByClient(true);
        contractDAO.update(contract);
    }

    public void unLockContract(String contractId) {
        Long conId = Long.parseLong(contractId);
        Contract contract = contractDAO.read(conId);
        contract.setBlockedByClient(false);
        contractDAO.update(contract);
    }
}
