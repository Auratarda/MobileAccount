package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;

import java.util.List;

/**
 * ClientService.
 */
public interface ClientService {

    /**
     * View contract.
     */
    Client login(String email, String password);

    List<Contract> viewContracts(Client client);

    /**
     * Modify contract.
     */
    void changeTariff(String tariffId);

    void addOption(String optionId);

    void removeOption(String optionId);

    void lockContract(String contractId);
}
