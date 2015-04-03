package com.tsystems.javaschool.service.services.Impl;

import com.tsystems.javaschool.domain.dao.*;
import com.tsystems.javaschool.domain.entities.*;
import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.service.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OperatorServiceImpl.
 */
@Service("operatorService")
@Transactional
public class OperatorServiceImpl implements OperatorService {
    private final static Logger logger = Logger.getLogger(OperatorService.class);

    @Autowired
    @Qualifier("ClientDAOImpl")
    private ClientDAO clientDAO;
    @Autowired
    @Qualifier("ContractDAOImpl")
    private ContractDAO contractDAO;
    @Autowired
    @Qualifier("TariffDAOImpl")
    private TariffDAO tariffDAO;
    @Autowired
    @Qualifier("OptionDAOImpl")
    private OptionDAO optionDAO;
    @Autowired
    @Qualifier("RoleDAOImpl")
    private RoleDAO roleDAO;

    public OperatorServiceImpl() {
    }

    private Client createNewUser(String firstName, String lastName, String dateOfBirth,
                                 String address, String passport, String email, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date birthday = null;
        try {
            birthday = sdf.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Client client = new Client(firstName, lastName, birthday, address, passport, email, password);
        return client;
    }

    public void createNewClient(String firstName, String lastName, String dateOfBirth,
                                String address, String passport, String email, String password,
                                String number, String selectedTariff, String[] selectedOptions) {
        logger.debug("Creating new client");

        Client client = createNewUser(firstName, lastName, dateOfBirth, address, passport, email, password);
        client = clientDAO.create(client);
        Role role = new Role("CLIENT");
        client.getRoles().add(role);
        Contract contract = contractDAO.findContractByNumber(number);
        client.getContracts().add(contract);
        contract.setClient(client);
        Tariff tariff = tariffDAO.findTariffByName(selectedTariff);
        contract.setTariff(tariff);
        List<Option> options = new ArrayList<>();
        for (String selectedOption : selectedOptions) {
            options.add(optionDAO.findOptionByName(selectedOption));
        }
        contract.setOptions(options);
        contractDAO.update(contract);
        clientDAO.update(client);
    }

    public void createNewAdmin(String firstName, String lastName, String dateOfBirth,
                               String address, String passport, String email, String password) {
        logger.debug("Creating new admin");
        Role role = new Role("ADMIN");
        Client admin = createNewUser(firstName, lastName, dateOfBirth, address, passport, email, password);
        admin.getRoles().add(role);
        clientDAO.create(admin);
    }

    public void createNewTariff(String name, Long price) {
        logger.debug("Creating a new tariff");
        Tariff tariff = new Tariff(name, price);
        tariffDAO.create(tariff);

    }

    public void createNewContract(String number) {
        logger.debug("Adding new contract");
        Contract contract = new Contract(number, false, false);
        contractDAO.create(contract);
    }

    public void createNewOption(String name, Long optionPrice, Long connectionCost) {
        logger.debug("Adding new option");
        Option option = new Option(name, optionPrice, connectionCost);
        optionDAO.create(option);
    }

    public void createNewRole(String role) {
        logger.debug("Creating a new role");
        Role newRole = new Role(role);
        roleDAO.create(newRole);
    }

    /**
     * Find entities.
     */
    public ClientDTO findClientByNumber(String number) {
        Client client = contractDAO.findClientByNumber(number);
        return EntityToDTOConverter.clientToDTO(client);
    }

    public ContractDTO findContractByNumber(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
        return EntityToDTOConverter.contractToDTO(contract);
    }

    public TariffDTO findTariffByName(String tariffName) {
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        return EntityToDTOConverter.tariffToDTO(tariff);
    }

    public OptionDTO findOptionByName(String optionName) {
        Option option = optionDAO.findOptionByName(optionName);
        return EntityToDTOConverter.optionToDTO(option);
    }


    /**
     * Modify a contract.
     */
    public void setNumber(ClientDTO clientDTO, String number) throws ClientNotFoundException {
        logger.debug("Setting a contract to a client");
        Client client = clientDAO.identify(clientDTO.getFirstName(), clientDTO.getLastName(),
                clientDTO.getEmail());
        Contract contract = contractDAO.findContractByNumber(number);
        contract.setClient(client);
        client.getContracts().add(contract);
        contractDAO.update(contract);
        clientDAO.update(client);
    }

    public void setTariff(ContractDTO contractDTO, TariffDTO tariffDTO) {
        logger.debug("Setting tariff to contract");
        Contract contract = contractDAO.findContractByNumber(contractDTO.getNumber());
        Tariff tariff = tariffDAO.findTariffByName(tariffDTO.getName());
        contract.setTariff(tariff);
        contractDAO.update(contract);
    }

    public void addOptions(ContractDTO contractDTO, List<OptionDTO> optionDTOs) {
        Contract contract = contractDAO.findContractByNumber(contractDTO.getNumber());
        List<Option> options = new ArrayList<Option>(0);
        for (OptionDTO optionDTO : optionDTOs) {
            options.add(optionDAO.findOptionByName(optionDTO.getName()));
        }
        for (Option option : options) {
            contract.getOptions().add(option);
        }
        contractDAO.update(contract);
    }

    public void updateContract(ContractDTO contractDTO) {
        Contract contract = contractDAO.findContractByNumber(contractDTO.getNumber());
        contractDAO.update(contract);
    }

    public void updateTariff(TariffDTO tariffDTO) {
        Tariff tariff = tariffDAO.findTariffByName(tariffDTO.getName());
        tariffDAO.update(tariff);
    }

    public void removeClient(ClientDTO clientDTO) throws ClientNotFoundException {
        logger.debug("Removing client " + clientDTO.getFirstName() + ", " + clientDTO.getLastName());
        Client client = clientDAO.identify(clientDTO.getFirstName(), clientDTO.getLastName(),
                clientDTO.getEmail());
        List<Contract> contracts = client.getContracts();
        for (Contract contract : contracts) {
            contract.setClient(null);
            contract.setTariff(null);
            List<Option> options = new ArrayList<Option>(0);
            contract.setOptions(options);
        }
        client.setContracts(new ArrayList<Contract>(0));
        List<Role> roles = client.getRoles();
        client.setRoles(new ArrayList<Role>(0));
        for (Role role : roles) {
            roleDAO.remove(role);
        }
        clientDAO.remove(client);
    }

    /**
     * View all clients, contracts, tariffs. Find client by ID.
     */
    public List<ClientDTO> findAllClients() {
        logger.debug("Reading all clients");
        List<Client> clients = clientDAO.getAll();
        List<ClientDTO> clientDTOs = new ArrayList<ClientDTO>(0);
        for (Client client : clients) {
            clientDTOs.add(EntityToDTOConverter.clientToDTO(client));
        }
        return clientDTOs;
    }

    public List<ContractDTO> findAllContracts() {
        logger.debug("Reading all contracts");
        List<Contract> contracts = contractDAO.findAllContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
            contractDTOs.add(EntityToDTOConverter.contractToDTO(contract));
        }
        return contractDTOs;
    }

    public List<ContractDTO> findFreeContracts() {
        logger.debug("Reading free numbers");
        List<Contract> contracts = contractDAO.findFreeNumbers();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
            contractDTOs.add(EntityToDTOConverter.contractToDTO(contract));
        }
        return contractDTOs;
    }

    public List<TariffDTO> findAllTariffs() {
        logger.debug("Reading all tariffs");
        List<Tariff> tariffs = tariffDAO.getAll();
        List<TariffDTO> tariffDTOs = new ArrayList<TariffDTO>();
        for (Tariff tariff : tariffs) {
            tariffDTOs.add(EntityToDTOConverter.tariffToDTO(tariff));
        }
        return tariffDTOs;
    }

    public List<OptionDTO> findAllOptions() {
        logger.debug("Reading all options");
        List<Option> options = optionDAO.getAll();
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>(0);
        for (Option option : options) {
            optionDTOs.add(EntityToDTOConverter.optionToDTO(option));
        }
        return optionDTOs;
    }

    /**
     * Lock/unlock contracts.
     */
    public void lockContract(ContractDTO contractDTO) {
        logger.debug("Blocking a contract by Operator");
        Contract contract = contractDAO.findContractByNumber(contractDTO.getNumber());
        contract.setBlockedByOperator(true);
        contractDAO.update(contract);
    }

    public void unlockContract(ContractDTO contractDTO) {
        logger.debug("Unlocking a contract by Operator");
        Contract contract = contractDAO.findContractByNumber(contractDTO.getNumber());
        contract.setBlockedByOperator(false);
        contractDAO.update(contract);
    }

    /**
     * Modify a tariff.
     */
    public void removeTariff(TariffDTO tariffDTO) {
        logger.debug("Removing a tariff");
        Tariff tariff = tariffDAO.findTariffByName(tariffDTO.getName());
        tariffDAO.remove(tariff);
    }

    public void removeOption(OptionDTO optionDTO) {
        logger.debug("Removing an option");
        Option option = optionDAO.findOptionByName(optionDTO.getName());
        optionDAO.remove(option);
    }

}
