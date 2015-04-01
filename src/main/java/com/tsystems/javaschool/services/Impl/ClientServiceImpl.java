package com.tsystems.javaschool.services.Impl;

import com.tsystems.javaschool.dao.ClientDAO;
import com.tsystems.javaschool.dao.ContractDAO;
import com.tsystems.javaschool.dao.OptionDAO;
import com.tsystems.javaschool.dao.TariffDAO;
import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.services.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

import static com.tsystems.javaschool.services.Impl.EntityToDTOConverter.clientToDTO;

/**
 * ClientServiceImpl.
 */
@Service("clientService")
@Transactional
public class ClientServiceImpl implements ClientService {
    private final static Logger logger = Logger.getLogger(ClientServiceImpl.class);

    @Autowired
    @Qualifier("ClientDAOImpl")
    private ClientDAO clientDAO;
    @Autowired
    @Qualifier("ContractDAOImpl")
    private ContractDAO contractDAO;
    @Autowired
    @Qualifier("TariffDAOImpl")
    private TariffDAO tariffDAO;
    @Autowired
    @Qualifier("OptionDAOImpl")
    private OptionDAO optionDAO;

    public ClientServiceImpl() {
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

    public ClientDTO login(String email, String password) throws ClientNotFoundException {
        Client client = clientDAO.login(email, password);
        return clientToDTO(client);
    }


    public void changeTariff(String contractNumber, String tariffName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }


    public void addOption(String contractNumber, String optionName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Option option = optionDAO.findOptionByName(optionName);
        List<Option> contractOptions = contract.getOptions();
        List<Option> incompatibleOptions = option.getIncompatibleOptions();
        List<Option> requiredOptions = option.getRequiredOptions();

        logger.debug("contractOptions " + optionsToString(contractOptions));
        logger.debug("incompatibleOptions " + optionsToString(incompatibleOptions));
        logger.debug("requiredOptions " + optionsToString(requiredOptions));

        // TODO: Create method isOptionCompatible it will help you to make code easier to understand.
        // TODO: It is better to check if options is compatible on higher level of abstraction
        // TODO: and returns more understandable result.
        contract.getOptions().add(option);
        contractDAO.update(contract);
    }

    // TODO: where will you check if contract is blocked?

    public void removeOption(String contractNumber, String optionName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Option option = optionDAO.findOptionByName(optionName);
        Iterator<Option> it = contract.getOptions().iterator();

        while (it.hasNext()){
            Option opt = it.next();
            if(opt.getOptionId() == option.getOptionId()){
                break;
            }
        }
        it.remove();
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
