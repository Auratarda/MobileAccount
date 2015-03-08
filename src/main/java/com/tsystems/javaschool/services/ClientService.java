package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.exceptions.*;

import java.util.List;

/**
 * ClientService.
 */
public interface ClientService {

    /**
     * View contract.
     */
    Client login(String email, String password) throws LoginException;

    List<Contract> viewContracts(String clientId);

    /**
     * Modify contract.
     */
    void changeTariff(String contractNumber, String tariffName) throws TariffNotSupportedOptionException, ContractIsBlockedException;

    void addOption(String v, String optionName) throws IncompatibleOptionException, RequiredOptionException, ContractIsBlockedException;

    void removeOption(String contractNumber, String optionName) throws RequiredOptionException, ContractIsBlockedException;

    void lockContract(String contractNumber);

    void unLockContract(String contractNumber);
}
