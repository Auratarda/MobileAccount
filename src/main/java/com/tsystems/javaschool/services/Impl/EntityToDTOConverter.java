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
        List<Contract> contracts = client.getContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        List<Role> roles = client.getRoles();
        List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>(0);
        for (Contract contract : contracts) {
            contractDTOs.add(contractToDTO(contract));
        }
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role.getRole());
            roleDTOs.add(roleDTO);
        }
        return new ClientDTO(client.getFirstName(), client.getLastName(),
                dateOfBirth, client.getAddress(), client.getPassport(), client.getEmail(),
                contractDTOs, roleDTOs);
    }

    public static ContractDTO contractToDTO(Contract contract) {
        ClientDTO clientDTO = clientToDTO(contract.getClient());
        TariffDTO tariffDTO = tariffToDTO(contract.getTariff());
        List<Option> options = contract.getOptions();
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>(0);
        for (Option option : options) {
            optionDTOs.add(optionToDTO(option));
        }
        return new ContractDTO(contract.getNumber(), clientDTO, contract.getBlockedByClient(),
                contract.getBlockedByOperator(), tariffDTO, optionDTOs);
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
