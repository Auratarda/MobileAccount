package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.exceptions.IncompatibleOptionException;
import com.tsystems.javaschool.exceptions.LoginException;
import com.tsystems.javaschool.exceptions.RequiredOptionException;
import com.tsystems.javaschool.exceptions.TariffNotSupportedOptionException;

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
    void changeTariff(String contractId, String tariffId) throws TariffNotSupportedOptionException;

    void addOption(String contractId, String optionId) throws IncompatibleOptionException, RequiredOptionException;

    void removeOption(String contractId, String optionId) throws RequiredOptionException;

    void lockContract(String contractId);

    void unLockContract(String contractId);
}
