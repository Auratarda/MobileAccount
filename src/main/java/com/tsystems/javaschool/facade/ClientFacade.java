package com.tsystems.javaschool.facade;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

/**
 * ClientService.
 */
public interface ClientFacade {

    /**
     * View contract.
     */
    ClientDTO login(String email, String password) throws ClientNotFoundException;

    /**
     * Modify contract.
     */
    void changeTariff(String contractNumber, String tariffName);

    String[] removeOption(String contractNumber, String optionName);

    void lockContract(String contractNumber);

    void unLockContract(String contractNumber);
}
