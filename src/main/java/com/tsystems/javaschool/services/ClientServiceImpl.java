package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.IncompatibleOptionException;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.exceptions.RequiredOptionException;
import com.tsystems.javaschool.exceptions.TariffNotSupportedOptionException;
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

    // TODO: better call it optionsToString it is more readable and have meaning
    // TODO: set always mean setter for something
    private static String setToMessage(Set<Option> set) {
        String message = "";
        int size = set.size();
        for (Option item : set) {
            message += item.getName();
            message += (--size == 0) ? "." : ", ";
        }
        return message;
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

    // TODO: where will you check if contract is blocked?
    public void changeTariff(String contractId, String tariffId) throws TariffNotSupportedOptionException {
        Long conId = Long.parseLong(contractId);
        Long tarId = Long.parseLong(tariffId);
        Contract contract = contractDAO.read(conId);
        Tariff tariff = tariffDAO.read(tarId);
        Set<Option> contractOptions = contract.getOptions();
        Set<Option> tariffOptions = tariff.getOptions();

        logger.debug("contractOptions " + setToMessage(contractOptions));
        logger.debug("tariffOptions " + setToMessage(tariffOptions));

        // TODO: how do you explain to customer what was happened?
        if (!tariffOptions.containsAll(contractOptions)) {
            contractOptions.removeAll(tariffOptions);
            throw new TariffNotSupportedOptionException("Tariff not supported options: "
                    + setToMessage(contractOptions));
        }
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    // TODO: where will you check if contract is blocked?
    public void addOption(String contractId, String optionId) throws IncompatibleOptionException, RequiredOptionException {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        Option option = optionDAO.read(optId);
        Set<Option> contractOptions = contract.getOptions();
        Set<Option> incompatibleOptions = option.getIncompatibleOptions();
        Set<Option> requiredOptions = option.getRequiredOptions();

        logger.debug("contractOptions " + setToMessage(contractOptions));
        logger.debug("incompatibleOptions " + setToMessage(incompatibleOptions));
        logger.debug("requiredOptions " + setToMessage(requiredOptions));

        // TODO: Create method isOptionCompatible it will help you to make code easier to understand.
        // TODO: It is better to check if options is compatible on higher level of abstraction
        // TODO: and returns more understandable result.
        for (Option incompatibleOption : incompatibleOptions) {
            if (contractOptions.contains(incompatibleOption)) {
                contractOptions.retainAll(incompatibleOptions);
                throw new IncompatibleOptionException("Incompatible Options: "
                        + setToMessage(incompatibleOptions));
            }
        }
        if (requiredOptions.size() > 0) {
            throw new RequiredOptionException("Required Options: "
                    + setToMessage(requiredOptions));
        }
        contract.getOptions().add(option);
        contractDAO.update(contract);
    }

    // TODO: where will you check if contract is blocked?
    public void removeOption(String contractId, String optionId) throws RequiredOptionException {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        Option option = optionDAO.read(optId);
        Set<Option> requiredOptions = option.getRequiredOptions();
        logger.debug("requiredOptions " + setToMessage(requiredOptions));
        if (requiredOptions.size() > 0) {
            throw new RequiredOptionException("Required Options: "
                    + setToMessage(requiredOptions));
        }
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
