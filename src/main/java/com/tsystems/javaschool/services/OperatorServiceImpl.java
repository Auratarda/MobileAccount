package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Set;

/**
 * OperatorServiceImpl.
 */
public class OperatorServiceImpl implements OperatorService {
    final static Logger logger = Logger.getLogger(OperatorService.class);
    EntityManager em;

    private ClientDAO clientDao;
    private ContractDAO contractDAO;
    private TariffDAO tariffDAO;
    private OptionDAO optionDAO;

    public OperatorServiceImpl(EntityManager em) {
        logger.info("Creating operator service");
        this.em = em;
        clientDao = new ClientDAOImpl(em);
        contractDAO = new ContractDAOImpl(em);
        tariffDAO = new TariffDAOImpl(em);
        optionDAO = new OptionDAOImpl(em);
    }

    public void addNewClient(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password) {
        Client client = new Client(firstName, lastName, dateOfBirth, address, passport, email, password);
        clientDao.create(client);
    }

    public void addContract(String number) {

    }

    public void setNumber(Long clientId, String number) {

    }

    public void setTariff(Long contractId, Tariff tariff) {

    }

    public void setOptions(Long contractId, Option option) {

    }

    public Set<Client> findAllClients() {
        return null;
    }

    public Set<Contract> findAllContracts() {
        return null;
    }

    public void lockContract(Long id) {

    }

    public void unLockContract(Long id) {

    }

    public Client findById(Long id) {
        return null;
    }

    public void changeTariff(Long contractId, Long tariffId) {

    }

    public void addOption(Long contractId, Long optionId) {

    }

    public void removeOption(Long contractId, Long optionId) {

    }

    public void addTariff(Long tariffId) {

    }

    public void removeTariff(Long tariffId) {

    }

    public void addTariffOption(Long tariffId, Long optionId) {

    }

    public void removeTariffOption(Long tariffId, Long optionId) {

    }

    public void addRequiredOption(Long optionId, Long reqOptionId) {

    }

    public void removeRequiredOption(Long optionId, Long reqOptionId) {

    }

    public void addIncompatibleOption(Long optionId, Long incOptionId) {

    }

    public void removeIncompatibleOption(Long optionId, Long incOptionId) {

    }
}
