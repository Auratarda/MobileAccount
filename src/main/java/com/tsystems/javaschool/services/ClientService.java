package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.exceptions.*;

import java.util.Set;

/**
 * ClientService.
 */
public interface ClientService {

    /**
     * View contract.
     */
    Client login(String email, String password) throws LoginException;

    Set<Contract> viewContracts(String clientId);

    /**
     * Modify contract.
     */
    void changeTariff(String contractId, String tariffId) throws TariffNotSupportedOptionException, ContractIsBlockedException;

    void addOption(String contractId, String optionId) throws IncompatibleOptionException, RequiredOptionException, ContractIsBlockedException;

    void removeOption(String contractId, String optionId) throws RequiredOptionException, ContractIsBlockedException;

    void lockContract(String contractId);

    void unLockContract(String contractId);
}
