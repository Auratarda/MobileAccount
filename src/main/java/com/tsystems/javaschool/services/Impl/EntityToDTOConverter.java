package com.tsystems.javaschool.services.Impl;

import com.tsystems.javaschool.dto.*;
import com.tsystems.javaschool.entities.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EntityToDTOServiceImpl. Convert to DTO methods.
 */
public class EntityToDTOConverter {

    public static ClientDTO clientToDTO(Client client) {
        Date date = client.getDateOfBirth();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateOfBirth = sdf.format(date);
        ClientDTO clientDTO = new ClientDTO(client.getFirstName(), client.getLastName(),
                dateOfBirth, client.getAddress(), client.getPassport(), client.getEmail());
        List<Contract> contracts = client.getContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
            contractDTOs.add(contractToDTO(contract, clientDTO));
        }
        clientDTO.setContracts(contractDTOs);
        List<Role> roles = client.getRoles();
        List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>(0);
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role.getRole());
            roleDTOs.add(roleDTO);
        }
        clientDTO.setRoles(roleDTOs);
        return clientDTO;
    }

    public static ContractDTO contractToDTO(Contract contract, ClientDTO client) {
        TariffDTO tariffDTO = tariffToDTO(contract.getTariff());
        List<Option> options = contract.getOptions();
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>(0);
        for (Option option : options) {
            optionDTOs.add(optionToDTO(option));
        }
        ContractDTO contractDTO = new ContractDTO(contract.getNumber(), client, contract.getBlockedByClient(),
                contract.getBlockedByOperator(), tariffDTO);
        contractDTO.setOptions(optionDTOs);
        return contractDTO;
    }

    public static ContractDTO contractToDTO(Contract contract) {
        Client client = contract.getClient();
        ClientDTO clientDTO = clientToDTO(client);
        List<ContractDTO> contractDTOs = clientDTO.getContracts();
        for (ContractDTO contractDTO : contractDTOs) {
            if (contractDTO.getNumber().equals(contract.getNumber())) {
                return contractDTO;
            }
        }
        return null;
    }

    public static TariffDTO tariffToDTO(Tariff tariff) {
        return new TariffDTO(tariff.getName(), tariff.getPrice());
    }

    public static OptionDTO optionToDTO(Option option) {
        return new OptionDTO(option.getName(), option.getPrice(), option.getConnectionCost());
    }

    public static RoleDTO roleToDTO(Role role) {
        return new RoleDTO(role.getRole());
    }
}
