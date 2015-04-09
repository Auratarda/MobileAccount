package com.tsystems.javaschool.service.services.Impl;

import com.tsystems.javaschool.domain.dao.ClientDAO;
import com.tsystems.javaschool.domain.dao.ContractDAO;
import com.tsystems.javaschool.domain.dao.OptionDAO;
import com.tsystems.javaschool.domain.dao.TariffDAO;
import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.domain.entities.Contract;
import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.domain.entities.Tariff;
import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.service.services.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public List<ClientDTO> getAllDTO() {
        List<ClientDTO> temp = new ArrayList<>();
        for(Client tC : clientDAO.getAll()){
            temp.add(tC.toDTO());
        }
        return temp;
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
//        return EntityToDTOConverter.clientToDTO(client);
        return client.toDTO();
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

        contract.getOptions().add(option);
        contractDAO.update(contract);
    }


    public void removeOption(String contractNumber, String optionName) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        Option option = optionDAO.findOptionByName(optionName);
        List<Option> requiredOptions = option.getRequiredOptions();
        Iterator<Option> it = contract.getOptions().iterator();

        while (it.hasNext()){
            Option opt = it.next();
            if(opt.getOptionId() == option.getOptionId()){
                break;
            }
        }
        it.remove();
        contract.getOptions().removeAll(requiredOptions);
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
