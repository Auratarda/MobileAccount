package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.ContractDTO;
import com.tsystems.javaschool.dto.OptionDTO;
import com.tsystems.javaschool.dto.TariffDTO;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AdminController.
 */
@Controller
@RequestMapping("/main")
public class AdminController {
    private final static Logger logger = Logger.getLogger(ClientController.class);

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
        ModelAndView contractsView = new ModelAndView("admin/contracts");
        List<ContractDTO> allContracts = operatorService.findAllContracts();
        contractsView.addObject("allContracts", allContracts);
        return contractsView;
    }


    @RequestMapping(value = "/showAllContracts", method = RequestMethod.POST)
    public ModelAndView showAllContracts() {
        return prepareContracts();
    }

    @RequestMapping(value = "/showAllTariffs", method = RequestMethod.POST)
    public ModelAndView showAllTariffs() {
        ModelAndView tariffsView = new ModelAndView("admin/tariffs");
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        tariffsView.addObject("allTariffs", allTariffs);
        return tariffsView;
    }

    @RequestMapping(value = "/showAllOptions", method = RequestMethod.POST)
    public ModelAndView showAllOptions() {
        ModelAndView optionsView = new ModelAndView("admin/options");
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/showClient", method = RequestMethod.POST)
    public ModelAndView showCliet(@RequestParam Map<String, String> requestParams) {
        String number = requestParams.get("number");
        return prepareAccount(number);
    }

    @RequestMapping(value = "/assignNewContract", method = RequestMethod.POST)
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

    @RequestMapping(value = "/addNewClient", method = RequestMethod.POST)
    public ModelAndView addNewClient(@RequestParam Map<String, String> requestParams) {
        String firstName = requestParams.get("firstName");
        String lastName = requestParams.get("lastName");
        String dateOfBirth = requestParams.get("dateOfBirth");
        String address = requestParams.get("address");
        String passport = requestParams.get("passport");
        String email = requestParams.get("email");
        String password = requestParams.get("password");
        String selectedNumber = requestParams.get("selectedNumber");
        String selectedTariff = requestParams.get("selectedTariff");
        operatorService.createNewClient(firstName, lastName, dateOfBirth, address, passport, email, password);
        ClientDTO client;
        ContractDTO contract;
        TariffDTO tariff;
        try {
            client = clientService.login(email, password);
            operatorService.setNumber(client, selectedNumber);
            contract = operatorService.findContractByNumber(selectedNumber);
            tariff = operatorService.findTariffByName(selectedTariff);
            operatorService.setTariff(contract, tariff);
        } catch (ClientNotFoundException e) {
            logger.error("Creating new client error");
        }
        return prepareContracts();
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
    public ModelAndView lockContractByOperator(@RequestParam Map<String, String> requestParams) {
        String numberToLock = requestParams.get("contractNumber");
        ContractDTO contractToLock = operatorService.findContractByNumber(numberToLock);
        operatorService.lockContract(contractToLock);
        ModelAndView accountView = prepareAccount(numberToLock);
        String status = "Заблокирован оператором";
        accountView.addObject("status", status);
        return accountView;
    }

    @RequestMapping(value = "/unlockContractByOperator", method = RequestMethod.POST)
    public ModelAndView unlockContractByOperator(@RequestParam Map<String, String> requestParams) {
        String numberToUnlock = requestParams.get("contractNumber");
        ContractDTO contractToUnlock = operatorService.findContractByNumber(numberToUnlock);
        operatorService.unlockContract(contractToUnlock);
        ModelAndView accountView = prepareAccount(numberToUnlock);
        String status;
        if (contractToUnlock.getBlockedByClient()) status = "Заблокирован клиентом";
        else status = "Не заблокирован";
        accountView.addObject("status", status);
        return accountView;
    }

    @RequestMapping(value = "/findClientByNumber", method = RequestMethod.POST)
    public ModelAndView findClientByNumber(@RequestParam Map<String, String> requestParams) {
        String searchNumber = requestParams.get("searchNumber");
        List<ContractDTO> contracts = operatorService.findAllContracts();
        for (ContractDTO contract : contracts) {
            if (searchNumber.equals(contract.getNumber())) {
                return prepareAccount(searchNumber);
            }
        }
        ModelAndView contractsView = prepareContracts();
        String notFound = "notFound";
        contractsView.addObject("notFound", notFound);
        return contractsView;
    }

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.POST)
    public ModelAndView addNewTariff(@RequestParam Map<String, String> requestParams) {
        String tariffName = requestParams.get("tariffName");
        Long tariffPrice = Long.parseLong(requestParams.get("tariffPrice"));
        operatorService.createNewTariff(tariffName, tariffPrice);
        List<TariffDTO> tariffs = operatorService.findAllTariffs();
        ModelAndView tariffsView = new ModelAndView("admin/tariffs");
        tariffsView.addObject("tariffs", tariffs);
        return tariffsView;
    }

    @RequestMapping(value = "/removeTariff", method = RequestMethod.POST)
    public ModelAndView removeTariff(@RequestParam Map<String, String> requestParams) {
        String tariffName = requestParams.get("tariffName");
        TariffDTO tariffToRemove = operatorService.findTariffByName(tariffName);
        operatorService.removeTariff(tariffToRemove);
        List<TariffDTO> tariffs = operatorService.findAllTariffs();
        ModelAndView tariffsView = new ModelAndView("admin/tariffs");
        tariffsView.addObject("tariffs", tariffs);
        return tariffsView;
    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.POST)
    public ModelAndView addNewOption(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        Long optionPrice = Long.parseLong(requestParams.get("optionPrice"));
        Long connectionCost = Long.parseLong(requestParams.get("connectionCost"));
        operatorService.createNewOption(optionName, optionPrice, connectionCost);
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        ModelAndView optionsView = new ModelAndView("admin/options");
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/removeOldOption", method = RequestMethod.POST)
    public ModelAndView removeOldOption(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        OptionDTO optionToRemove = operatorService.findOptionByName(optionName);
        operatorService.removeOption(optionToRemove);
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        ModelAndView optionsView = new ModelAndView("admin/options");
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/removeOptionFromContract", method = RequestMethod.POST)
    public ModelAndView removeOptionFromContract(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        String contractNumber = requestParams.get("currentContract");
        clientService.removeOption(contractNumber, optionName);
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/setTariff", method = RequestMethod.POST)
    public ModelAndView setTariff(@RequestParam Map<String, String> requestParams) {
        String selectedTariff = requestParams.get("selectedTariff");
        String contractNumber = requestParams.get("contractNumber");
        TariffDTO tariff = operatorService.findTariffByName(selectedTariff);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        operatorService.setTariff(contract, tariff);
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/addMoreOptions", method = RequestMethod.POST)
    public ModelAndView addMoreOptions(@RequestParam Map<String, String> requestParams) {
        String selectedOption = requestParams.get("selectedOption");
        String contractNumber = requestParams.get("contractNumber");
        OptionDTO option = operatorService.findOptionByName(selectedOption);
        List<OptionDTO> options = new ArrayList<>(0);
        options.add(option);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        operatorService.addOptions(contract, options);
        return prepareAccount(contractNumber);
    }

}