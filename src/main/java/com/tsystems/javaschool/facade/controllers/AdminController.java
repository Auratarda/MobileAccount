package com.tsystems.javaschool.facade.controllers;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.service.services.ClientService;
import com.tsystems.javaschool.service.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AdminController.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    private ModelAndView prepareAccount(String contractNumber) {
        ClientDTO client = operatorService.findClientByNumber(contractNumber);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        TariffDTO tariff = contract.getTariff();
        List<OptionDTO> options = contract.getOptions();
        String status = "Не заблокирован";
        if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
        if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        Iterator<OptionDTO> iter = allOptions.iterator();
        while (iter.hasNext()){
            OptionDTO optionDTO = iter.next();
            for (OptionDTO option : options) {
                if (optionDTO.getName().equals(option.getName())) iter.remove();
            }
        }
        ModelAndView accountView = new ModelAndView("admin/account");
        accountView.addObject("contract", contract);
        accountView.addObject("client", client);
        accountView.addObject("tariff", tariff);
        accountView.addObject("options", options);
        accountView.addObject("status", status);
        accountView.addObject("tariffs", allTariffs);
        accountView.addObject("allOptions", allOptions);
        return accountView;
    }

    private ModelAndView prepareContracts() {
        ModelAndView contractsView = new ModelAndView("admin");
        List<ContractDTO> allContracts = operatorService.findAllContracts();
        contractsView.addObject("allContracts", allContracts);
        return contractsView;
    }


    @RequestMapping(value = "/showAllContracts", method = RequestMethod.GET)
    public ModelAndView showAllContracts() {
        ModelAndView adminView = new ModelAndView("admin");
        List<ContractDTO> allContracts = operatorService.findAllContracts();
        adminView.addObject("allContracts", allContracts);
        return adminView;
    }

    @RequestMapping(value = "/showAllTariffs", method = RequestMethod.GET)
    public ModelAndView showAllTariffs() {
        ModelAndView tariffsView = new ModelAndView("admin/tariffs");
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        tariffsView.addObject("allTariffs", allTariffs);
        return tariffsView;
    }

    @RequestMapping(value = "/showAllOptions", method = RequestMethod.GET)
    public ModelAndView showAllOptions() {
        ModelAndView optionsView = new ModelAndView("admin/options");
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/showClient", method = RequestMethod.POST)
    public ModelAndView showClient(@RequestParam String number) {
        return prepareAccount(number);
    }

    @RequestMapping(value = "/assignNewContract", method = RequestMethod.GET)
    public ModelAndView assignNewContract() {
        ModelAndView assignContractView = new ModelAndView("admin/assignContract");
        List<ContractDTO> freeContracts = operatorService.findFreeContracts();
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        assignContractView.addObject("freeContracts", freeContracts);
        assignContractView.addObject("tariffs", allTariffs);
        assignContractView.addObject("allOptions", allOptions);
        return assignContractView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search() {
        return new ModelAndView("admin/search");
    }

    @RequestMapping(value = "/searchByNumber", method = RequestMethod.POST)
    public ModelAndView searchByNumber(@RequestParam String searchNumber) {
        List<ContractDTO> contracts = operatorService.findAllContracts();
        for (ContractDTO contract : contracts) {
            if (searchNumber.equals(contract.getNumber())) {
                return prepareAccount(searchNumber);
            }
        }
        return search();
    }

    @RequestMapping(value = "/addNewClient", method = RequestMethod.POST)
    public ModelAndView addNewClient(@RequestParam Map<String, String> requestParams,
                                     @RequestParam(value = "selectedOptions[]",
                                             required = false) String[] selectedOptions) {
        String firstName = requestParams.get("firstName");
        String lastName = requestParams.get("lastName");
        String dateOfBirth = requestParams.get("dateOfBirth");
        String address = requestParams.get("address");
        String passport = requestParams.get("passport");
        String email = requestParams.get("email");
        String password = requestParams.get("password");
        String selectedNumber = requestParams.get("selectedNumber");
        String selectedTariff = requestParams.get("selectedTariff");
        operatorService.createNewClient(firstName, lastName, dateOfBirth, address, passport,
                email, password, selectedNumber, selectedTariff, selectedOptions);
        return showClient(selectedNumber);
    }

    @RequestMapping(value = "/removeClient", method = RequestMethod.POST)
    public ModelAndView removeClient(@RequestParam Map<String, String> requestParams) {
        String contractToRemove = requestParams.get("contractToRemove");
        ClientDTO clientToRemove = operatorService.findClientByNumber(contractToRemove);
        List<ContractDTO> freeContracts = operatorService.findFreeContracts();
        List<ContractDTO> contractsToFree = clientToRemove.getContracts();
        for (ContractDTO contract : contractsToFree) {
            freeContracts.add(contract);
        }
        try {
            operatorService.removeClient(clientToRemove);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        return prepareContracts();
    }

    @RequestMapping(value = "/lockContractByOperator", method = RequestMethod.POST)
    public ModelAndView lockContractByOperator(@RequestParam String number) {
        ContractDTO contractToLock = operatorService.findContractByNumber(number);
        operatorService.lockContract(contractToLock);
        ModelAndView accountView = prepareAccount(number);
        String status = "Заблокирован оператором";
        accountView.addObject("status", status);
        return accountView;
    }

    @RequestMapping(value = "/unlockContractByOperator", method = RequestMethod.POST)
    public ModelAndView unlockContractByOperator(@RequestParam String number) {
        ContractDTO contractToUnlock = operatorService.findContractByNumber(number);
        operatorService.unlockContract(contractToUnlock);
        ModelAndView accountView = prepareAccount(number);
        String status;
        if (contractToUnlock.getBlockedByClient()) status = "Заблокирован клиентом";
        else status = "Не заблокирован";
        accountView.addObject("status", status);
        return accountView;
    }

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.POST)
    public ModelAndView addNewTariff(@RequestParam Map<String, String> requestParams) {
        String tariffName = requestParams.get("tariffName");
        Long tariffPrice = Long.parseLong(requestParams.get("tariffPrice"));
        operatorService.createNewTariff(tariffName, tariffPrice);
        return showAllTariffs();
    }

    @RequestMapping(value = "/removeTariff", method = RequestMethod.POST)
    public ModelAndView removeTariff(@RequestParam String tariffName) {
        TariffDTO tariffToRemove = operatorService.findTariffByName(tariffName);
        operatorService.removeTariff(tariffToRemove);
        return showAllTariffs();
    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.POST)
    public ModelAndView addNewOption(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        Long optionPrice = Long.parseLong(requestParams.get("optionPrice"));
        Long connectionCost = Long.parseLong(requestParams.get("connectionCost"));
        operatorService.createNewOption(optionName, optionPrice, connectionCost);
        return showAllOptions();
    }

    @RequestMapping(value = "/removeOldOption", method = RequestMethod.POST)
    public ModelAndView removeOldOption(@RequestParam String optionName) {
        OptionDTO optionToRemove = operatorService.findOptionByName(optionName);
        operatorService.removeOption(optionToRemove);
        return showAllOptions();
    }

    @RequestMapping(value = "/removeOptionFromContract", method = RequestMethod.POST)
    public ModelAndView removeOptionFromContract(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        String contractNumber = requestParams.get("number");
        clientService.removeOption(contractNumber, optionName);
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/setTariff", method = RequestMethod.POST)
    public ModelAndView setTariff(@RequestParam Map<String, String> requestParams) {
        String selectedTariff = requestParams.get("selectedTariff");
        String contractNumber = requestParams.get("number");
        TariffDTO tariff = operatorService.findTariffByName(selectedTariff);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        operatorService.setTariff(contract, tariff);
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/addMoreOptions", method = RequestMethod.POST)
    public ModelAndView addMoreOptions(@RequestParam String number,
                                       @RequestParam(value = "selectedOptions[]",
                                               required = false) String[] selectedOptions) {
        ContractDTO contract = operatorService.findContractByNumber(number);
        List<OptionDTO> options = new ArrayList<>(0);
        for (String selectedOption : selectedOptions) {
            options.add(operatorService.findOptionByName(selectedOption));
        }
        operatorService.addOptions(contract, options);
        return prepareAccount(number);
    }

}