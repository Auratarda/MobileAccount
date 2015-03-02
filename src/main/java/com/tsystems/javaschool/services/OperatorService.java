package com.tsystems.javaschool.services;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;

import java.util.Date;
import java.util.Set;

/**
 * ClientService.
 */
public interface OperatorService {

    /**
     * Assign a new contract.
     */
    void addNewClient(String firstName, String lastName, Date dateOfBirth, String address, String passport, String email, String password);

    void addContract(String number);

    void setNumber(Long clientId, String number);

    void setTariff(Long contractId, Tariff tariff);

    void setOptions(Long contractId, Option option);

    /**
     * View all clients and contracts.
     */
    Set<Client> findAllClients();

    Set<Contract> findAllContracts();

    /**
     * Lock/unlock contracts.
     */
    void lockContract(Long id);

    void unLockContract(Long id);

    /**
     * Find client by id.
     */
    Client findById(Long id);

    /**
     * Change tariff. Add/remove options.
     */
    void changeTariff(Long contractId, Long tariffId);

    void addOption(Long contractId, Long optionId);

    void removeOption(Long contractId, Long optionId);

    /**
     * Add/remove tariff.
     */
    void addTariff(Long tariffId);

    void removeTariff(Long tariffId);

    /**
     * Add/remove tariff options.
     */
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