package com.tsystems.javaschool.service.services;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
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
                         String number, String tariff, String[] selectedOptions);

    void createNewContract(String number);

    void createNewTariff(String name, Long price);

    void createNewOption(String name, Long optionPrice, Long connectionCost);

    void createNewRole(String role);

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

    /**
     * View all clients and contracts. Find client by ID.
     */
    List<ClientDTO> findAllClients();

    List<ContractDTO> findAllContracts();

    List<TariffDTO> findAllTariffs();

    List<OptionDTO> findAllOptions();

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

}