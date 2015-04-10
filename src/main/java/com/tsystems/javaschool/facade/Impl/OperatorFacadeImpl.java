package com.tsystems.javaschool.facade.Impl;

import com.tsystems.javaschool.domain.dao.*;
import com.tsystems.javaschool.domain.entities.*;
import com.tsystems.javaschool.facade.OperatorFacade;
import com.tsystems.javaschool.facade.dto.*;
import com.tsystems.javaschool.service.ClientService;
import com.tsystems.javaschool.service.ContractService;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This class serves functions available for users with the role "ADMIN".
 * It invokes services, converts entities to DTO and returns result to the controllers.
 */
@Service("operatorFacade")
public class OperatorFacadeImpl implements OperatorFacade {
    private final static Logger log = Logger.getLogger(OperatorFacade.class);

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;
    @Autowired
    @Qualifier("contractService")
    private ContractService contractService;


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

    public OperatorFacadeImpl() {
    }

    /*
    * This method creates new Client with the role "CLIENT".
    **/
    public void createNewClient(String firstName, String lastName, String dateOfBirth,
                                String address, String passport, String email, String password,
                                String number, String selectedTariff) {
        log.debug("Creating new client");
        clientService.createNewClient(firstName, lastName, dateOfBirth, address, passport, email,
                password, number, selectedTariff);
    }

    /*
    * This method creates new Client with the role "ADMIN".
    **/
    public void createNewAdmin(String firstName, String lastName, String dateOfBirth,
                               String address, String passport, String email, String password) {
        log.debug("Creating new admin");
        clientService.createNewAdmin(firstName, lastName, dateOfBirth, address, passport, email, password);
    }


    public void createNewTariff(String name, Long price) {
        log.debug("Creating a new tariff");
        Tariff tariff = new Tariff(name, price);
        tariffDAO.create(tariff);
    }

    public void createNewContract(String number) {
        log.debug("Adding new contract");
        contractService.createNewContract(number);
    }

    public void createNewOption(String name, Long optionPrice, Long connectionCost) {
        log.debug("Adding new option");
        Option option = new Option(name, optionPrice, connectionCost);
        optionDAO.create(option);
    }

    public void createNewRole(String role) {
        log.debug("Creating a new role");
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
        return clientService.findClientByNumber(number).toDTO();
    }

    public ContractDTO findContractByNumber(String contractNumber) {
        return contractService.findContractByNumber(contractNumber).toDTO();
    }

    public TariffDTO findTariffByName(String tariffName) {
        Tariff tariff = tariffDAO.findTariffByName(tariffName);
        return tariff.toDTO();
    }

    public OptionDTO findOptionByName(String optionName) {
        Option option = optionDAO.findOptionByName(optionName);
        return option.toDTO();
    }


    /**
     * Modify a contract.
     */
    public void setNumber(ClientDTO clientDTO, String number) throws ClientNotFoundException {
        log.debug("Setting a contract to a client");
        Client client = clientDAO.identify(clientDTO.getFirstName(), clientDTO.getLastName(),
                clientDTO.getEmail());
        Contract contract = contractDAO.findContractByNumber(number);
        contract.setClient(client);
        client.getContracts().add(contract);
        contractDAO.update(contract);
        clientDAO.update(client);
    }

    public String[] changeTariff(String contractNumber, String tariffName) {
        log.debug("Changing tariff. Contract: " + contractNumber + " New tariff: " + tariffName);
        List<Option> unacceptableOptions = contractService.changeTariff(contractNumber, tariffName);
        String [] optionNames = new String[unacceptableOptions.size()];
        for (int i = 0; i < unacceptableOptions.size(); i++) {
            optionNames[i] = unacceptableOptions.get(i).getName();
        }
        return optionNames;
    }

    public String [] addOptions(String contractNumber, String[] optionNames) {
        log.debug("Adding options. Contract: " + contractNumber + " Number of options: " + optionNames.length);
        List<Option> incompatibleOptions = contractService.addOptionsToContract(contractNumber, optionNames);
        String [] incOptionNames = new String[incompatibleOptions.size()];
        for (int i = 0; i < incompatibleOptions.size(); i++) {
            optionNames[i] = incompatibleOptions.get(i).getName();
        }
        return incOptionNames;
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
        log.debug("Removing client " + clientDTO.getFirstName() + ", " + clientDTO.getLastName());
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
        log.debug("Reading all clients");
        List<Client> clients = clientDAO.getAll();
        List<ClientDTO> clientDTOs = new ArrayList<ClientDTO>(0);
        for (Client client : clients) {
//            clientDTOs.add(EntityToDTOConverter.clientToDTO(client));
            clientDTOs.add(client.toDTO());
        }
        return clientDTOs;
    }

    public List<ContractDTO> findAllContracts() {
        log.debug("Reading all contracts");
        List<Contract> contracts = contractService.findAllContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
            contractDTOs.add(contract.toDTO());
        }
        return contractDTOs;
    }

    public List<ContractDTO> findFreeContracts() {
        log.debug("Reading free numbers");
        List<Contract> contracts = contractDAO.findFreeNumbers();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>(0);
        for (Contract contract : contracts) {
//            contractDTOs.add(EntityToDTOConverter.contractToDTO(contract));
            contractDTOs.add(contract.toDTO());
        }
        return contractDTOs;
    }

    public List<TariffDTO> findAllTariffs() {
        log.debug("Reading all tariffs");
        List<Tariff> tariffs = tariffDAO.getAll();
        List<TariffDTO> tariffDTOs = new ArrayList<TariffDTO>();
        for (Tariff tariff : tariffs) {
            tariffDTOs.add(tariff.toDTO());
        }
        return tariffDTOs;
    }

    public List<OptionDTO> findAllOptions() {
        log.debug("Reading all options");
        List<Option> options = optionDAO.getAll();
        List<OptionDTO> optionDTOs = new ArrayList<OptionDTO>(0);
        for (Option option : options) {
//            optionDTOs.add(EntityToDTOConverter.optionToDTO(option));
            optionDTOs.add(option.toDTO());
        }
        return optionDTOs;
    }

    public List<OptionDTO> findOptionsByTariff(String tariffName) {
        log.debug("Reading all options by tariff");
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
    public void lockContractByOperator(String contractNumber) {
        log.debug("Blocking a contract by Operator");
       contractService.lockContractByOperator(contractNumber);
    }

    public void unlockContractByOperator(String contractNumber) {
        log.debug("Unlocking a contract by Operator");
        contractService.unlockContractByOperator(contractNumber);
    }

    /**
     * Modify a tariff.
     */
    public void removeTariff(TariffDTO tariffDTO) {
        log.debug("Removing a tariff");
        Tariff tariff = tariffDAO.findTariffByName(tariffDTO.getName());
        tariffDAO.remove(tariff);
    }

    public String[] removeOption(String contractNumber, String optionName) {
        log.debug("Removing option. Contract: " + contractNumber + " Option: " + optionName);
        List<Option> requiredOptions = contractService.removeOptionFromContract(contractNumber, optionName);
        String [] optionNames = new String[requiredOptions.size()];
        for (int i = 0; i < requiredOptions.size(); i++) {
            optionNames[i] = requiredOptions.get(i).getName();
        }
        return optionNames;
    }

    public String[] dropOption(String optionName){
        return null;
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

    public List intersect (List listToRetain, List listToRemove) {
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
        List<OptionDTO> unacceptableOptions = intersect(contractOptions, tariffOptions);
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
