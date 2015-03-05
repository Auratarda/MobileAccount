package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;

import java.util.Date;
import java.util.List;

/**
 * ClientService.
 */
public interface OperatorService {


    /**
     * Create new entities.
     */
    void createNewClient(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password);

    void createNewContract(String number);

    void createNewTariff(String name, Long price);

    void createNewOption(String name, Long optionPrice, Long connectionCost);

    /**
     * Find entities.
     */
    Client findClientByNumber(String number);

    Client findClientByID(Long clientId);

    Contract findContractByID(Long contractId);

    Tariff findTariffByID(Long tariffId);

    Option findOptionByID(Long optionId);

    /**
     * Modify a contract.
     */

    void setNumber(Long clientId, Long contractId);

    void setTariff(Long contractId, Long tariffId); //Also change tariff.

    void addOption(Long contractId, Long optionId);

    void removeOption(Long contractId, Long optionId);

    /**
     * View all clients and contracts. Find client by ID.
     */
    List<Client> findAllClients();

    List<Contract> findAllContracts();

    List<Tariff> findAllTariffs();

    List<Option> findAllOptions();

    /**
     * Lock/unlock contracts.
     */
    void lockContract(Long contractId);

    void unLockContract(Long contractId);

    /**
     * Modify a tariff.
     */
    void removeTariff(Long tariffId);

    void addTariffOption(Long tariffId, Long optionId);

    void removeTariffOption(Long tariffId, Long optionId);

    /**
     * Add/remove rules for required and incompatible options.
     */
    void addRequiredOption(Long optionId, Long reqOptionId);

    void removeRequiredOption(Long optionId, Long reqOptionId);

    void addIncompatibleOption(Long optionId, Long incOptionId);

    void removeIncompatibleOption(Long optionId, Long incOptionId);
}