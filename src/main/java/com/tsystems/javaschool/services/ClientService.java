package com.tsystems.javaschool.services;

import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.exceptions.*;

/**
 * ClientService.
 */
public interface ClientService {

    /**
     * View contract.
     */
    ClientDTO login(String email, String password) throws ClientNotFoundException;

    /**
     * Modify contract.
     */
    void changeTariff(String contractNumber, String tariffName) throws TariffNotSupportedOptionException, ContractIsBlockedException;

    void addOption(String v, String optionName) throws IncompatibleOptionException, RequiredOptionException, ContractIsBlockedException;

    void removeOption(String contractNumber, String optionName) throws RequiredOptionException, ContractIsBlockedException;

    void lockContract(String contractNumber);

    void unLockContract(String contractNumber);
}
