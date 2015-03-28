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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Component
public class AdminController {
    private final static Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    private ModelAndView prepareClient(String contractNumber, String path) {
        ClientDTO client = operatorService.findClientByNumber(contractNumber);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        TariffDTO tariff = contract.getTariff();
        List<OptionDTO> options = contract.getOptions();
        String status = "Не заблокирован";
        if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
        if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
        ModelAndView accountView = new ModelAndView(path);
        accountView.addObject("contract", contract);
        accountView.addObject("client", client);
        accountView.addObject("tariff", tariff);
        accountView.addObject("options", options);
        accountView.addObject("status", status);
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

    @RequestMapping(value = "/showCliet", method = RequestMethod.POST)
    public ModelAndView showCliet(@RequestParam Map<String, String> requestParams) {
        String number = requestParams.get("number");
        String path = "admin/account";
        return prepareClient(number, path);
    }

    @RequestMapping(value = "/assignNewContract", method = RequestMethod.POST)
    public ModelAndView assignNewContract() {
        ModelAndView assignContractView = new ModelAndView("admin/assignContract");
        List<ContractDTO> freeContracts = operatorService.findFreeContracts();
        assignContractView.addObject("freeContracts", freeContracts);
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
    public String lockContractByOperator(@RequestParam Map<String, String> requestParams, Model model) {
        String numberToLock = requestParams.get("contractNumber");
        ContractDTO contractToLock = operatorService.findContractByNumber(numberToLock);
        operatorService.lockContract(contractToLock);
        String status = "Заблокирован оператором";
        model.addAttribute("status", status);
        return "account";
    }

    @RequestMapping(value = "/unlockContractByOperator", method = RequestMethod.POST)
    public String unlockContractByOperator(@RequestParam Map<String, String> requestParams, Model model) {
        String numberToUnlock = requestParams.get("contractNumber");
        ContractDTO contractToUnlock = operatorService.findContractByNumber(numberToUnlock);
        operatorService.unlockContract(contractToUnlock);
        String status;
        if (contractToUnlock.getBlockedByClient()) status = "Заблокирован клиентом";
        else status = "Не заблокирован";
        model.addAttribute("status", status);
        return "account";
    }

    @RequestMapping(value = "/findClientByNumber", method = RequestMethod.POST)
    public String findClientByNumber(@RequestParam Map<String, String> requestParams, Model model) {
        String searchNumber = requestParams.get("searchNumber");
        List<ContractDTO> contracts = operatorService.findAllContracts();
        model.addAttribute("contracts", contracts);
        for (ContractDTO contract : contracts) {
            if (searchNumber.equals(contract.getNumber())) {
//                prepareClient(searchNumber);
                return "account";
            }
        }
        String notFound = "notFound";
        model.addAttribute("notFound", notFound);
        return "contracts";
    }

    @RequestMapping(value = "/addNewTariff", method = RequestMethod.POST)
    public String addNewTariff(@RequestParam Map<String, String> requestParams, Model model) {
        String tariffName = requestParams.get("tariffName");
        Long tariffPrice = Long.parseLong(requestParams.get("tariffPrice"));
        operatorService.createNewTariff(tariffName, tariffPrice);
        List<TariffDTO> tariffs = operatorService.findAllTariffs();
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }

    @RequestMapping(value = "/removeTariff", method = RequestMethod.POST)
    public String removeTariff(@RequestParam Map<String, String> requestParams, Model model) {
        String tariffName = requestParams.get("tariffName");
        TariffDTO tariffToRemove = operatorService.findTariffByName(tariffName);
        operatorService.removeTariff(tariffToRemove);
        List<TariffDTO> tariffs = operatorService.findAllTariffs();
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.POST)
    public String addNewOption(@RequestParam Map<String, String> requestParams, Model model) {
        String optionName = requestParams.get("optionName");
        Long optionPrice = Long.parseLong(requestParams.get("optionPrice"));
        Long connectionCost = Long.parseLong(requestParams.get("connectionCost"));
        operatorService.createNewOption(optionName, optionPrice, connectionCost);
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        model.addAttribute("allOptions", allOptions);
        return "options";
    }

    @RequestMapping(value = "/removeOldOption", method = RequestMethod.POST)
    public String removeOldOption(@RequestParam Map<String, String> requestParams, Model model) {
        String optionName = requestParams.get("optionName");
        OptionDTO optionToRemove = operatorService.findOptionByName(optionName);
        operatorService.removeOption(optionToRemove);
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        model.addAttribute("allOptions", allOptions);
        return "options";
    }

    @RequestMapping(value = "/removeOptionFromContract", method = RequestMethod.POST)
    public String removeOptionFromContract(@RequestParam Map<String, String> requestParams, Model model) {
        String optionName = requestParams.get("optionName");
        String contractNumber = requestParams.get("currentContract");
        clientService.removeOption(contractNumber, optionName);
        return "account";
    }

    @RequestMapping(value = "/setTariff", method = RequestMethod.POST)
    public String setTariff(@RequestParam Map<String, String> requestParams, Model model) {
        String selectedTariff = requestParams.get("selectedTariff");
        String contractNumber = requestParams.get("contractNumber");
        TariffDTO tariff = operatorService.findTariffByName(selectedTariff);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        operatorService.setTariff(contract, tariff);
        model.addAttribute("tariff", tariff);
        return "account";
    }

    @RequestMapping(value = "/addMoreOptions", method = RequestMethod.POST)
    public String addMoreOptions(@RequestParam Map<String, String> requestParams, Model model) {
        String selectedOption = requestParams.get("selectedOption");
        String contractNumber = requestParams.get("contractNumber");
        OptionDTO option = operatorService.findOptionByName(selectedOption);
        List<OptionDTO> options = new ArrayList<OptionDTO>(0);
        options.add(option);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        operatorService.addOptions(contract, options);
//        prepareClient(contractNumber, model);
        return "account";
    }

}