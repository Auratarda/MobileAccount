package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
import com.tsystems.javaschool.exceptions.LoginException;

import java.util.Date;
import java.util.List;

/**
 * ClientService.
 */
public interface OperatorService {


    /**
     * Create new entities.
     */
    void createNewAdmin(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password);

    void createNewClient(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password);

    void createNewClient(String firstName, String lastName, String birthday, String address, String passport, String email, String password);

    void createNewContract(String number);

    void createNewTariff(String name, Long price);

    void createNewOption(String name, Long optionPrice, Long connectionCost);

    void createNewRole(String role);

    /**
     * Find entities.
     */
    Client findClientByNumber(String number);

    Client findClientByEmailAndPassword(String email, String password) throws LoginException;

    Contract findContractByNumber(String contractNumber);

    Tariff findTariffByName(String tariffName);

    Option findOptionByName(String optionName);

    Client findClientByID(Long clientId);

    Contract findContractByID(Long contractId);

    Tariff findTariffByID(Long tariffId);

    Option findOptionByID(Long optionId);

    /**
     * Modify a contract.
     */

    void setNumber(Client client, String number);

    void setTariff(Long contractId, Long tariffId); //Also change tariff.

    void updateContract(Contract contract);

    void updateTariff(Tariff tariff);

    void addOption(Long contractId, Long optionId);

    void removeOption(Long contractId, Long optionId);

    void removeClient(Client client);

    /**
     * View all clients and contracts. Find client by ID.
     */
    List<Client> findAllClients();

    List<Contract> findAllContracts();

    List<Tariff> findAllTariffs();

    List<Option> findAllOptions();

    List<Contract> findFreeContracts();

    /**
     * Lock/unlock contracts.
     */
    void lockContract(Contract contract);

    void unLockContract(Contract contract);

    /**
     * Modify a tariff.
     */
    void removeTariff(Tariff tariff);

    void removeOption(Option option);

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