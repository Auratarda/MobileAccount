package com.tsystems.javaschool.facade.Impl;

import com.tsystems.javaschool.domain.entities.Client;
import com.tsystems.javaschool.domain.entities.Option;
import com.tsystems.javaschool.facade.ClientFacade;
import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.service.ClientService;
import com.tsystems.javaschool.service.ContractService;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class serves functions available for users with the role "CLIENT".
 * It invokes services, converts entities to DTO and returns result to the controllers.
 */
@Service("clientFacade")
public class ClientFacadeImpl implements ClientFacade {
    private final static Logger log = Logger.getLogger(ClientFacade.class);

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;
    @Autowired
    @Qualifier("contractService")
    private ContractService contractService;

    public ClientFacadeImpl() {
    }

    /**
     * This method receives email and password and returns a ClientDTO.
     */
    public ClientDTO login(String email, String password) throws ClientNotFoundException {
        log.debug("Searching for client with email: " + email + " and password: " + password);
        Client client = clientService.login(email, password);
        return client.toDTO();
    }


    /*
     * This method reassigns a Contract to another Tariff.
     **/
    public void changeTariff(String contractNumber, String tariffName) {
        log.debug("Changing tariff. Contract: " + contractNumber + " New tariff: " + tariffName);
        contractService.changeTariff(contractNumber, tariffName);
    }

    /*
     * This method adds several Options to a Contract.
     **/
    public String[] removeOption(String contractNumber, String optionName) {
        log.debug("Removing option. Contract: " + contractNumber + " Option: " + optionName);
        List<Option> requiredOptions = contractService.removeOptionFromContract(contractNumber, optionName);
        String [] optionNames = new String[requiredOptions.size()];
        for (int i = 0; i < requiredOptions.size(); i++) {
            optionNames[i] = requiredOptions.get(i).getName();
        }
        return optionNames;
    }

    /*
     * This method locks a Contract by Client.
     * The client could unlock it without the help of operator.
     * No changes could be made while the Contract is locked.
     **/
    public void lockContract(String contractNumber) {
        log.debug("Contract: " + contractNumber + " is locked by Client.");
        contractService.lockContractByClient(contractNumber);
    }

    /*
    * This method unlocks a Contract by Client.
    **/
    public void unLockContract(String contractNumber) {
        log.debug("Contract: " + contractNumber + " is locked by Client.");
        contractService.unlockContractByClient(contractNumber);
    }
}
