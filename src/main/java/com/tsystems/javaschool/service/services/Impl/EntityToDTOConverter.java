package com.tsystems.javaschool.service.services.Impl;

import com.tsystems.javaschool.domain.entities.*;
import com.tsystems.javaschool.facade.dto.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EntityToDTOServiceImpl. Convert to DTO methods.
 */
public class EntityToDTOConverter {

    public static ClientDTO clientToDTO(Client client) {
        String dateOfBirth = "";
        if (client.getDateOfBirth() != null){
            Date date = client.getDateOfBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            dateOfBirth = sdf.format(date);
        }
        ClientDTO clientDTO = new ClientDTO(client.getFirstName(), client.getLastName(),
                dateOfBirth, client.getAddress(), client.getPassport(), client.getEmail());
        List<Contract> contracts = client.getContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>();
        for (Contract contract : contracts) {
            contractDTOs.add(contractToDTO(contract, clientDTO));
        }
        clientDTO.setContracts(contractDTOs);
        List<Role> roles = client.getRoles();
        List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
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
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>();
        for (Option option : options) {
            optionDTOs.add(optionToDTO(option));
        }
        ContractDTO contractDTO = new ContractDTO(contract.getNumber(), client, contract.getBlockedByClient(),
                contract.getBlockedByOperator(), tariffDTO);
        contractDTO.setOptions(optionDTOs);
        return contractDTO;
    }

    public static ContractDTO contractToDTO(Contract contract) {
        if (contract.getClient() == null) return new ContractDTO(contract.getNumber(),
                contract.getBlockedByClient(), contract.getBlockedByOperator());
        return contractToDTO(contract, clientToDTO(contract.getClient()));
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
