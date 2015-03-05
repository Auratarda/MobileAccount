package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.*;
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
    // TODO: Done
    private static String optionsToString(Set<Option> set) {
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
    // TODO: Done
    public void changeTariff(String contractId, String tariffId) throws TariffNotSupportedOptionException, ContractIsBlockedException {
        Long conId = Long.parseLong(contractId);
        Long tarId = Long.parseLong(tariffId);
        Contract contract = contractDAO.read(conId);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        Tariff tariff = tariffDAO.read(tarId);
        Set<Option> contractOptions = contract.getOptions();
        Set<Option> tariffOptions = tariff.getOptions();

        logger.debug("contractOptions " + optionsToString(contractOptions));
        logger.debug("tariffOptions " + optionsToString(tariffOptions));

        // TODO: how do you explain to customer what was happened?
        // TODO: I will catch exception in servlet, parse the message
        // TODO: and notify the client
        // TODO: Not yet done
        if (!tariffOptions.containsAll(contractOptions)) {
            contractOptions.removeAll(tariffOptions);
            throw new TariffNotSupportedOptionException("Tariff not supported options: "
                    + optionsToString(contractOptions));
        }
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    // TODO: where will you check if contract is blocked?
    // TODO: Done
    public void addOption(String contractId, String optionId) throws IncompatibleOptionException, RequiredOptionException, ContractIsBlockedException {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        Option option = optionDAO.read(optId);
        Set<Option> contractOptions = contract.getOptions();
        Set<Option> incompatibleOptions = option.getIncompatibleOptions();
        Set<Option> requiredOptions = option.getRequiredOptions();

        logger.debug("contractOptions " + optionsToString(contractOptions));
        logger.debug("incompatibleOptions " + optionsToString(incompatibleOptions));
        logger.debug("requiredOptions " + optionsToString(requiredOptions));

        // TODO: Create method isOptionCompatible it will help you to make code easier to understand.
        // TODO: It is better to check if options is compatible on higher level of abstraction
        // TODO: and returns more understandable result.
        for (Option incompatibleOption : incompatibleOptions) {
            if (contractOptions.contains(incompatibleOption)) {
                contractOptions.retainAll(incompatibleOptions);
                throw new IncompatibleOptionException("Incompatible Options: "
                        + optionsToString(incompatibleOptions));
            }
        }
        if (requiredOptions.size() > 0) {
            throw new RequiredOptionException("Required Options: "
                    + optionsToString(requiredOptions));
        }
        contract.getOptions().add(option);
        contractDAO.update(contract);
    }

    // TODO: where will you check if contract is blocked?
    public void removeOption(String contractId, String optionId) throws RequiredOptionException, ContractIsBlockedException {
        Long conId = Long.parseLong(contractId);
        Long optId = Long.parseLong(optionId);
        Contract contract = contractDAO.read(conId);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        Option option = optionDAO.read(optId);
        Set<Option> requiredOptions = option.getRequiredOptions();
        logger.debug("requiredOptions " + optionsToString(requiredOptions));
        if (requiredOptions.size() > 0) {
            throw new RequiredOptionException("Required Options: "
                    + optionsToString(requiredOptions));
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
