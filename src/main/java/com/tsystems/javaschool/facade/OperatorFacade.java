package com.tsystems.javaschool.facade;

import com.tsystems.javaschool.facade.dto.*;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;

import java.util.List;

/**
 * ClientService.
 */
public interface OperatorFacade {


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

    String[] changeTariff(String contractNumber, String tariffName);

    String [] addOptions(String contractNumber, String[] optionNames);

    void removeClient(ClientDTO clientDTO) throws ClientNotFoundException;

    List<OptionDTO> prepareOptions(String [] selectedOptions);

    List<OptionDTO> checkOptionsCompatibility(List<OptionDTO> chosenOptions);

    /**
     * View all clients and contracts. Find client by ID.
     */
    List<ContractDTO> findAllContracts();

    List<TariffDTO> findAllTariffs();

    List<OptionDTO> findAllOptions();

    List<OptionDTO> findOptionsByTariff(String tariffName);

    List<ContractDTO> findFreeContracts();

    /**
     * Lock/unlock contracts.
     */
    void lockContractByOperator(String contractNumber);

    void unlockContractByOperator(String contractNumber);

    /**
     * Modify a tariff.
     */
    void removeTariff(TariffDTO tariffDTO);

    String[] removeOption(String contractNumber, String optionName);

    String[] dropOption(String optionName);

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

    List<OptionDTO> checkTariffOptions (String tariffName, String contractName);

    /**
     * Find remainder from two lists of Entities.
     */
    List intersect(List listToRetain, List listToRemove);

    List checkUniqueValues (List list);

    List removeItem(List list, String name);
}