package com.tsystems.javaschool.service.services;

import com.tsystems.javaschool.facade.dto.*;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

import java.util.List;

/**
 * ClientService.
 */
public interface OperatorService {


    /**
     * Create new entities.
     */
    void createNewAdmin(String firstName, String lastName, String dateOfBirth,
                        String address, String passport, String email, String password);

    void createNewClient(String firstName, String lastName, String dateOfBirth,
                         String address, String passport, String email, String password,
                         String number, String tariff);

    void createNewContract(String number);

    void createNewTariff(String name, Long price);

    void createNewOption(String name, Long optionPrice, Long connectionCost);

    void createNewRole(String role);

    List<InfoDTO> getInfo(String tariffName);

    /**
     * Find entities.
     */
    ClientDTO findClientByNumber(String number);

    ContractDTO findContractByNumber(String contractNumber);

    TariffDTO findTariffByName(String tariffName);

    OptionDTO findOptionByName(String optionName);

    /**
     * Modify a contract.
     */

    void setNumber(ClientDTO clientDTO, String number) throws ClientNotFoundException;

    void setTariff(ContractDTO contractDTO, TariffDTO tariffDTO); //Also change tariff.

    void addOptions(ContractDTO contractDTO, List<OptionDTO> optionDTOs);

    void updateContract(ContractDTO contractDTO);

    void updateTariff(TariffDTO tariffDTO);

    void removeClient(ClientDTO clientDTO) throws ClientNotFoundException;

    List<OptionDTO> prepareOptions(String [] selectedOptions);

    List<OptionDTO> checkTariffCompatibility(String selectedTariff, List<OptionDTO> chosenOption);

    List<OptionDTO> checkOptionsCompatibility(List<OptionDTO> chosenOptions);

    /**
     * View all clients and contracts. Find client by ID.
     */
    List<ClientDTO> findAllClients();

    List<ContractDTO> findAllContracts();

    List<TariffDTO> findAllTariffs();

    List<OptionDTO> findAllOptions();

    List<OptionDTO> findOptionsByTariff(String tariffName);

    List<ContractDTO> findFreeContracts();

    /**
     * Lock/unlock contracts.
     */
    void lockContract(ContractDTO contractDTO);

    void unlockContract(ContractDTO contractDTO);

    /**
     * Modify a tariff.
     */
    void removeTariff(TariffDTO tariffDTO);

    void removeOption(OptionDTO optionDTO);

    void removeOptionFromTariff(String optionName, String tariffName);

    void addTariffOptions(String tariffName, String[] options);

    /**
     * Modify an option.
     */
    void addRequiredOptions(String optionName, String[] selectedOptions);

    void removeRequiredOption(String currentOption, String optionToRemove);

    void removeIncompatibleOption(String currentOption, String optionToRemove);

    List<OptionDTO> getRequiredOptions (String optionName);

    void addIncompatibleOption(String optionName, String incOption);

    List<OptionDTO> getIncompatibleOptions(String optionName);

    /**
     * Find remainder from two lists of Entities.
     */
    List intercept(List listToRetain, List listToRemove);

    List checkUniqueValues (List list);

    List removeItem(List list, String name);

    List<OptionDTO> checkTariffOptions (String tariffName, String contractName);
}