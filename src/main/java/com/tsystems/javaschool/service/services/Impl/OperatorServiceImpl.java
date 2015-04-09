package com.tsystems.javaschool.service.services.Impl;

import com.tsystems.javaschool.domain.dao.*;
import com.tsystems.javaschool.domain.entities.*;
import com.tsystems.javaschool.facade.dto.*;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.service.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                                String number, String selectedTariff) {
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

    public List<InfoDTO> getInfo(String tariffName) {
        List<Contract> contracts = contractDAO.findContractsByTariff(tariffName);
        List<InfoDTO> infoDTOs = new ArrayList<>();
        for (Contract contract : contracts) {
            infoDTOs.add(new InfoDTO(contract.getClient().getFirstName(),
                    contract.getClient().getLastName(),
                    contract.getClient().getEmail(),
                    contract.getNumber()));
        }
        return infoDTOs;
    }

    /**
     * Find entities.
     */
    public ClientDTO findClientByNumber(String number) {
        Client client = contractDAO.findClientByNumber(number);
//        return EntityToDTOConverter.clientToDTO(client);
        return client.toDTO();
    }

    public ContractDTO findContractByNumber(String contractNumber) {
        Contract contract = contractDAO.findContractByNumber(contractNumber);
//        return EntityToDTOConverter.contractToDTO(contract);
        return contract.toDTO();
    }

    public TariffDTO findTariffByName(String tariffName) {
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
//        return EntityToDTOConverter.tariffToDTO(tariff);
        return tariff.toDTO();
    }

    public OptionDTO findOptionByName(String optionName) {
        Option option = optionDAO.findOptionByName(optionName);
//        return EntityToDTOConverter.optionToDTO(option);
        return option.toDTO();
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
        List<Option> optionsToAdd = new ArrayList<Option>(0);
        for (OptionDTO optionDTO : optionDTOs) {
            Option option = optionDAO.findOptionByName(optionDTO.getName());
            optionsToAdd.add(option);
            optionsToAdd.addAll(option.getRequiredOptions());
        }
        optionsToAdd = checkUniqueValues(optionsToAdd);
        for (Option option : optionsToAdd) {
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
//            clientDTOs.add(EntityToDTOConverter.clientToDTO(client));
            clientDTOs.add(client.toDTO());
        }
        return clientDTOs;
    }

    public List<ContractDTO> findAllContracts() {
        logger.debug("Reading all contracts");
        List<Contract> contracts = contractDAO.findAllContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
//            contractDTOs.add(EntityToDTOConverter.contractToDTO(contract));
            contractDTOs.add(contract.toDTO());
        }
        return contractDTOs;
    }

    public List<ContractDTO> findFreeContracts() {
        logger.debug("Reading free numbers");
        List<Contract> contracts = contractDAO.findFreeNumbers();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
//            contractDTOs.add(EntityToDTOConverter.contractToDTO(contract));
            contractDTOs.add(contract.toDTO());
        }
        return contractDTOs;
    }

    public List<TariffDTO> findAllTariffs() {
        logger.debug("Reading all tariffs");
        List<Tariff> tariffs = tariffDAO.getAll();
        List<TariffDTO> tariffDTOs = new ArrayList<TariffDTO>();
        for (Tariff tariff : tariffs) {
            tariffDTOs.add(tariff.toDTO());
        }
        return tariffDTOs;
    }

    public List<OptionDTO> findAllOptions() {
        logger.debug("Reading all options");
        List<Option> options = optionDAO.getAll();
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>(0);
        for (Option option : options) {
//            optionDTOs.add(EntityToDTOConverter.optionToDTO(option));
            optionDTOs.add(option.toDTO());
        }
        return optionDTOs;
    }

    public List<OptionDTO> findOptionsByTariff(String tariffName) {
        logger.debug("Reading all options by tariff");
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        List<OptionDTO> optionDTOs = new ArrayList<>();
        for (Option option : tariff.getOptions()) {
            optionDTOs.add(option.toDTO());
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

    //TODO: remove from Tariff_options, Required_options, Incompatible_options
    public void removeOption(OptionDTO optionDTO) {
        logger.debug("Removing an option");
        Option option = optionDAO.findOptionByName(optionDTO.getName());
//        Option req = option.getRequiredOptions().get(0);
//        option.getRequiredOptions().clear();
//        req.getRequiredOptions().clear();
//        optionDAO.update(option);
//        optionDAO.update(req);
        optionDAO.remove(option);
    }

    public void removeOptionFromTariff(String optionName, String tariffName) {
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        List<Option> tariffOptions = tariff.getOptions();
        Option optionToRemove = optionDAO.findOptionByName(optionName);
        List<Option> requiredOptions = optionToRemove.getRequiredOptions();
        Iterator iter = tariffOptions.iterator();
        while (iter.hasNext()) {
            Option option = (Option) iter.next();
            if (option.getName().equals(optionName)) iter.remove();
        }
        tariffOptions.removeAll(requiredOptions);
        tariffDAO.update(tariff);
    }

    public void addTariffOptions(String tariffName, String[] options) {
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        List<Option> tariffOptions = tariff.getOptions();
        List<Option> optionsToAdd = new ArrayList<>();
        for (String option : options) {
            Option currentOption = optionDAO.findOptionByName(option);
            optionsToAdd.add(currentOption);
            List<Option> requiredOptions = currentOption.getRequiredOptions();
            optionsToAdd.addAll(requiredOptions);
        }

        List<Option> result = checkUniqueValues(optionsToAdd);
        for (Option option : result) {
            tariffOptions.add(option);
        }
        tariffDAO.update(tariff);
    }


    public void addRequiredOptions(String optionName, String[] selectedOptions) {
        Option currentOption = optionDAO.findOptionByName(optionName);
        List<Option> options = new ArrayList<>();
        for (String selectedOption : selectedOptions) {
            options.add(optionDAO.findOptionByName(selectedOption));
        }
        for (Option option : options) {
            currentOption.getRequiredOptions().add(option);
            option.getRequiredOptions().add(currentOption);
            optionDAO.update(option);
        }
        optionDAO.update(currentOption);
    }

    public void addIncompatibleOption(String optionName, String incOption) {
        Option option = optionDAO.findOptionByName(optionName);
        List<Option> requiredOptions = new ArrayList<>(option.getRequiredOptions());
        requiredOptions.add(option);
        Option incompatitableOption = optionDAO.findOptionByName(incOption);
        List<Option> requiredIncOptions = new ArrayList<>(incompatitableOption.getRequiredOptions());
        requiredIncOptions.add(incompatitableOption);
        for (Option requiredOption : requiredOptions) {
            requiredOption.getIncompatibleOptions().addAll(requiredIncOptions);
            optionDAO.update(requiredOption);
        }
        for (Option requiredIncOption : requiredIncOptions) {
            requiredIncOption.getIncompatibleOptions().addAll(requiredOptions);
            optionDAO.update(requiredIncOption);
        }
    }

    public void removeRequiredOption(String currentOption, String optionToRemove) {
        Option option = optionDAO.findOptionByName(currentOption);
        Option remove = optionDAO.findOptionByName(optionToRemove);
        Iterator<Option> iter = option.getRequiredOptions().iterator();
        while (iter.hasNext()) {
            Option current = iter.next();
            if (current.getName().equals(remove.getName())) {
                iter.remove();
                break;
            }
        }
        iter = remove.getRequiredOptions().iterator();
        while (iter.hasNext()) {
            Option current = iter.next();
            if (current.getName().equals(option.getName())) {
                iter.remove();
                break;
            }
        }
        optionDAO.update(option);
        optionDAO.update(remove);
    }

    public void removeIncompatibleOption(String currentOption, String optionToRemove) {
        Option option = optionDAO.findOptionByName(currentOption);
        Option remove = optionDAO.findOptionByName(optionToRemove);
        Iterator<Option> iter = option.getIncompatibleOptions().iterator();
        while (iter.hasNext()) {
            Option current = iter.next();
            if (current.getName().equals(remove.getName())) {
                iter.remove();
                break;
            }
        }
        iter = remove.getIncompatibleOptions().iterator();
        while (iter.hasNext()) {
            Option current = iter.next();
            if (current.getName().equals(option.getName())) {
                iter.remove();
                break;
            }
        }
        optionDAO.update(option);
        optionDAO.update(remove);
    }

    public List<OptionDTO> getRequiredOptions(String optionName) {
        Option option = optionDAO.findOptionByName(optionName);
        List<Option> requiredOptions = option.getRequiredOptions();
        List<OptionDTO> result = new ArrayList<>();
        for (Option requiredOption : requiredOptions) {
            result.add(requiredOption.toDTO());
        }
        return result;
    }

    public List<OptionDTO> getIncompatibleOptions(String optionName) {
        Option option = optionDAO.findOptionByName(optionName);
        List<Option> incompatibleOptions = option.getIncompatibleOptions();
        List<OptionDTO> result = new ArrayList<>();
        for (Option incompatibleOption : incompatibleOptions) {
            result.add(incompatibleOption.toDTO());
        }
        return result;
    }

    public List intercept(List listToRetain, List listToRemove) {
        if (listToRetain == null || listToRemove == null || listToRetain.size() == 0
                || listToRemove.size() == 0) return listToRetain;
        Object sample = listToRetain.get(0);
        if (sample instanceof ContractDTO) {
            List<ContractDTO> result = new ArrayList<>(listToRetain);
            List<ContractDTO> remove = new ArrayList<>(listToRemove);
            Iterator iter = result.iterator();
            while (iter.hasNext()) {
                ContractDTO contract = (ContractDTO) iter.next();
                for (ContractDTO contractToRemove : remove) {
                    if (contract.getNumber().equals(contractToRemove.getNumber())) iter.remove();
                }
            }
            return (List<ContractDTO>) checkUniqueValues(result);
        }
        if (sample instanceof TariffDTO) {
            List<TariffDTO> result = new ArrayList<>(listToRetain);
            List<TariffDTO> remove = new ArrayList<>(listToRemove);
            Iterator iter = result.iterator();
            while (iter.hasNext()) {
                TariffDTO tariff = (TariffDTO) iter.next();
                for (TariffDTO tariffToRemove : remove) {
                    if (tariff.getName().equals(tariffToRemove.getName())) iter.remove();
                }
            }
            return (List<TariffDTO>) checkUniqueValues(result);
        }
        if (sample instanceof OptionDTO) {
            List<OptionDTO> result = new ArrayList<>(listToRetain);
            List<OptionDTO> remove = new ArrayList<>(listToRemove);
            Iterator iter = result.iterator();
            for (OptionDTO optionToRemove : remove) {
                while (iter.hasNext()) {
                    OptionDTO option = (OptionDTO) iter.next();
                    if (option.getName().equals(optionToRemove.getName())) iter.remove();
                }
                iter = result.iterator();
            }
            return (List<OptionDTO>) checkUniqueValues(result);
        }
        return listToRetain;
    }

    public List checkUniqueValues(List list) {
        if (list == null || list.size() == 0) return list;
        Object sample = list.get(0);
        if (sample instanceof Option) {
            Set<Option> s = new TreeSet<Option>(new Comparator<Option>() {

                @Override
                public int compare(Option o1, Option o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            s.addAll(list);
            List res = Arrays.asList(s.toArray());
            List<Option> result = new LinkedList<>();
            for (Object re : res) {
                result.add((Option) re);
            }
            return result;
        }
        if (sample instanceof OptionDTO) {
            Set<OptionDTO> s = new TreeSet<OptionDTO>(new Comparator<OptionDTO>() {

                @Override
                public int compare(OptionDTO o1, OptionDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            s.addAll(list);
            List res = Arrays.asList(s.toArray());
            List<OptionDTO> result = new LinkedList<>();
            for (Object re : res) {
                result.add((OptionDTO) re);
            }
            return result;
        }
        return list;
    }

    public List removeItem(List list, String name) {
        if (list == null || name == null || list.size() == 0) return list;
        Object sample = list.get(0);
        if (sample instanceof OptionDTO) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                OptionDTO option = (OptionDTO) iter.next();
                if (option.getName().equals(name)) iter.remove();
            }
        }
        return list;
    }

    public List<OptionDTO> checkTariffOptions(String tariffName, String contractNumber) {
        ContractDTO contract = findContractByNumber(contractNumber);
        List<OptionDTO> tariffOptions = findOptionsByTariff(tariffName);
        List<OptionDTO> contractOptions = contract.getOptions();
        List<OptionDTO> unacceptableOptions = intercept(contractOptions, tariffOptions);
        return unacceptableOptions;
    }

    public List<OptionDTO> prepareOptions(String[] selectedOptions) {
        List<OptionDTO> chosenOptions = new ArrayList<>();
        for (String selectedOption : selectedOptions) {
            OptionDTO option = findOptionByName(selectedOption);
            chosenOptions.add(option);
        }


        return checkUniqueValues(chosenOptions);
    }

    public List<OptionDTO> checkTariffCompatibility(String selectedTariff, List<OptionDTO> chosenOptions) {
        List<OptionDTO> tariffOptions = findOptionsByTariff(selectedTariff);
        return intercept(chosenOptions, tariffOptions);
    }

    public List<OptionDTO> checkOptionsCompatibility(List<OptionDTO> chosenOptions) {
        List<OptionDTO> allIncompatibleOptions = new ArrayList<>();
        for (OptionDTO chosenOption : chosenOptions) {
            allIncompatibleOptions.addAll(getIncompatibleOptions(chosenOption.getName()));
        }
        allIncompatibleOptions = (List<OptionDTO>) checkUniqueValues(allIncompatibleOptions);
        List<OptionDTO> incompatibleOptions = new ArrayList<>(chosenOptions);
        incompatibleOptions.removeAll(allIncompatibleOptions);

        return incompatibleOptions;
    }

}
