package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * OperatorServiceImpl.
 */
public class OperatorServiceImpl implements OperatorService {
    final static Logger logger = Logger.getLogger(OperatorService.class);
    EntityManager em;

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;
    private TariffDAO tariffDAO;
    private OptionDAO optionDAO;

    public OperatorServiceImpl(EntityManager em) {
        logger.info("Creating operator service");
        this.em = em;
        clientDAO = new ClientDAOImpl(em);
        contractDAO = new ContractDAOImpl(em);
        tariffDAO = new TariffDAOImpl(em);
        optionDAO = new OptionDAOImpl(em);
    }

    /**
     * Create new entities.
     */
    public void createNewClient(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password) {
        logger.info("Creating new client");
        Client client = new Client(firstName, lastName, dateOfBirth, address, passport, email, password);
        clientDAO.create(client);
    }

    public void createNewTariff(String name, Long price) {
        logger.info("Creating a new tariff");
        Tariff tariff = new Tariff(name, price);
        tariffDAO.create(tariff);

    }

    public void createNewContract(String number) {
        logger.info("Adding new contract");
        Contract contract = new Contract(number, false, false);
        contractDAO.create(contract);
    }

    public void createNewOption(String name, Long optionPrice, Long connectionCost) {
        logger.info("Adding new option");
        Option option = new Option(name, optionPrice, connectionCost);
        optionDAO.create(option);
    }

    /**
     * Modify a contract.
     */
    public void setNumber(Long clientId, Long contractId) {
        logger.info("Setting a contract to a client");
        Client client = clientDAO.read(clientId);
        Contract contract = contractDAO.read(contractId);
        contract.setClient(client);
        client.getNumbers().add(contract);
        contractDAO.update(contract);
        clientDAO.update(client);
    }

    public void setTariff(Long contractId, Long tariffId) {
        logger.info("Setting tariff to contract");
        Contract contract = contractDAO.read(contractId);
        Tariff tariff = tariffDAO.read(tariffId);
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    public void addOption(Long contractId, Long optionId) {
        logger.info("Setting option to contract");
        Contract contract = contractDAO.read(contractId);
        Option option = optionDAO.read(optionId);
        contract.getOptions().add(option);
        contractDAO.update(contract);
    }

    public void removeOption(Long contractId, Long optionId) {
        logger.info("Removing an option from a contract");
        Contract contract = contractDAO.read(contractId);
        Option option = optionDAO.read(optionId);
        contract.getOptions().remove(option);
        contractDAO.update(contract);
    }

    /**
     * View all clients and contracts. Find client by ID.
     */
    public List<Client> findAllClients() {
        logger.info("Reading all clients");
        return clientDAO.getAll();
    }

    public List<Contract> findAllContracts() {
        logger.info("Reading all contracts");
        return contractDAO.getAll();
    }

    public Client findById(Long clientId) {
        logger.info("Reading a client");
        return clientDAO.read(clientId);
    }

    /**
     * Lock/unlock contracts.
     */
    public void lockContract(Long contractId) {
        logger.info("Blocking a contract by Operator");
        Contract contract = contractDAO.read(contractId);
        contract.setBlockedByOperator(true);
        contractDAO.update(contract);
    }

    public void unLockContract(Long contractId) {
        logger.info("Unlocking a contract by Operator");
        Contract contract = contractDAO.read(contractId);
        contract.setBlockedByOperator(false);
        contractDAO.update(contract);
    }

    /**
     * Modify a tariff.
     */
    public void removeTariff(Long tariffId) {
        logger.info("Removing a tariff");
        Tariff tariff = tariffDAO.read(tariffId);
        tariffDAO.delete(tariff);
    }

    public void addTariffOption(Long tariffId, Long optionId) {
        logger.info("Adding an option to a tariff");
        Tariff tariff = tariffDAO.read(tariffId);
        Option option = optionDAO.read(optionId);
        tariff.getOptions().add(option);
        tariffDAO.update(tariff);
    }

    public void removeTariffOption(Long tariffId, Long optionId) {
        logger.info("Removing an option from a tariff");
        Tariff tariff = tariffDAO.read(tariffId);
        Option option = optionDAO.read(optionId);
        tariff.getOptions().remove(option);
        tariffDAO.update(tariff);
    }

    /**
     * Add/remove rules for required and incompatible options.
     */
    public void addRequiredOption(Long optionId, Long reqOptionId) {
        logger.info("Adding a new rule: required options");
        Option option = optionDAO.read(optionId);
        Option reqOption = optionDAO.read(reqOptionId);
        option.getRequiredOptions().add(reqOption);
        reqOption.getRequiredOptions().add(option);
        optionDAO.update(option);
        optionDAO.update(reqOption);
    }

    public void removeRequiredOption(Long optionId, Long reqOptionId) {
        logger.info("Removing a rule: required options");
        Option option = optionDAO.read(optionId);
        Option reqOption = optionDAO.read(reqOptionId);
        option.getRequiredOptions().remove(reqOption);
        reqOption.getRequiredOptions().remove(option);
        optionDAO.update(option);
        optionDAO.update(reqOption);
    }

    public void addIncompatibleOption(Long optionId, Long incOptionId) {
        logger.info("Adding a new rule: incompatible options");
        Option option = optionDAO.read(optionId);
        Option incOption = optionDAO.read(incOptionId);
        option.getRequiredOptions().add(incOption);
        incOption.getRequiredOptions().add(option);
        optionDAO.update(option);
        optionDAO.update(incOption);
    }

    public void removeIncompatibleOption(Long optionId, Long incOptionId) {
        logger.info("Removing a rule: incompatible options");
        Option option = optionDAO.read(optionId);
        Option incOption = optionDAO.read(incOptionId);
        option.getRequiredOptions().remove(incOption);
        incOption.getRequiredOptions().remove(option);
        optionDAO.update(option);
        optionDAO.update(incOption);
    }
}
