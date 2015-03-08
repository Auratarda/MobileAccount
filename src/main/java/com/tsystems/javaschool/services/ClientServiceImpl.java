package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dao.*;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.*;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

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

    private static String optionsToString(List<Option> set) {
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

    public List<Contract> viewContracts(String clientId) {
        Long id = Long.parseLong(clientId);
        Client client = clientDAO.read(id);
        return client.getContracts();
    }

    public void changeTariff(String contractNumber, String tariffName) throws TariffNotSupportedOptionException, ContractIsBlockedException {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        List<Option> contractOptions = contract.getOptions();
        List<Option> tariffOptions = tariff.getOptions();

        logger.debug("contractOptions " + optionsToString(contractOptions));
        logger.debug("tariffOptions " + optionsToString(tariffOptions));

        // TODO: how do you explain to customer what was happened?
        // TODO: I will catch exception in servlet, parse the message
        // TODO: and notify the client
        if (!tariffOptions.containsAll(contractOptions)) {
            contractOptions.removeAll(tariffOptions);
            throw new TariffNotSupportedOptionException("Tariff not supported options: "
                    + optionsToString(contractOptions));
        }
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    public void addOption(String contractNumber, String optionName) throws IncompatibleOptionException, RequiredOptionException, ContractIsBlockedException {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Option option = optionDAO.findOptionByName(optionName);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        List<Option> contractOptions = contract.getOptions();
        List<Option> incompatibleOptions = option.getIncompatibleOptions();
        List<Option> requiredOptions = option.getRequiredOptions();

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
    public void removeOption(String contractNumber, String optionName) throws RequiredOptionException, ContractIsBlockedException {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Option option = optionDAO.findOptionByName(optionName);
        if (contract.getBlockedByClient() || contract.getBlockedByOperator()) {
            throw new ContractIsBlockedException();
        }
        List<Option> requiredOptions = option.getRequiredOptions();
        logger.debug("requiredOptions " + optionsToString(requiredOptions));
        if (requiredOptions.size() > 0) {
            throw new RequiredOptionException("Required Options: "
                    + optionsToString(requiredOptions));
        }
        contract.getOptions().remove(option);
        contractDAO.update(contract);
    }

    public void lockContract(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByClient(true);
        contractDAO.update(contract);
    }

    public void unLockContract(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        contract.setBlockedByClient(false);
        contractDAO.update(contract);
    }
}
