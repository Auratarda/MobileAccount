package com.tsystems.javaschool.service.services;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

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
    void changeTariff(String contractNumber, String tariffName);

    void addOption(String v, String optionName);

    void removeOption(String contractNumber, String optionName);

    void lockContract(String contractNumber);

    void unLockContract(String contractNumber);
}
