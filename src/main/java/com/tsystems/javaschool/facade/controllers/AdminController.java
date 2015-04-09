package com.tsystems.javaschool.facade.controllers;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.service.services.ClientService;
import com.tsystems.javaschool.service.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    public OperatorService getOperatorService() {
        return operatorService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    private static final Logger log = Logger.getLogger(AdminController.class);

    private ModelAndView prepareAccount(String contractNumber) {
        log.info("Creating account");
        ClientDTO client = operatorService.findClientByNumber(contractNumber);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        TariffDTO tariff = contract.getTariff();
        String status = "Не заблокирован";
        if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
        if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        List<OptionDTO> tariffOptions = operatorService.findOptionsByTariff(tariff.getName());
        List<OptionDTO> contractOptions = contract.getOptions();
        List<OptionDTO> optionsToSelect = operatorService.intercept(tariffOptions, contractOptions);
        String setTariffError = "false";
        String setOptionError = "false";
        ModelAndView accountView = new ModelAndView("admin/account");
        accountView.addObject("contract", contract);
        accountView.addObject("client", client);
        accountView.addObject("tariff", tariff);
        accountView.addObject("options", contractOptions);
        accountView.addObject("status", status);
        accountView.addObject("tariffs", allTariffs);
        accountView.addObject("allOptions", optionsToSelect);
        accountView.addObject("setTariffError", setTariffError);
        accountView.addObject("setOptionError", setOptionError);
        return accountView;
    }

    private ModelAndView prepareContracts() {
        ModelAndView contractsView = new ModelAndView("admin");
        List<ContractDTO> allContracts = operatorService.findAllContracts();
        contractsView.addObject("allContracts", allContracts);
        return contractsView;
    }

    private ModelAndView prepareAssignNewContract() {
        ModelAndView assignContractView = new ModelAndView("admin/assignContract");
        List<ContractDTO> freeContracts = operatorService.findFreeContracts();
        List<TariffDTO> allTariffs = operatorService.findAllTariffs();
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        assignContractView.addObject("freeContracts", freeContracts);
        assignContractView.addObject("tariffs", allTariffs);
        assignContractView.addObject("allOptions", allOptions);
        String setTariffError = "false";
        assignContractView.addObject("setTariffError", setTariffError);
        return assignContractView;
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
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        tariffsView.addObject("allTariffs", allTariffs);
        tariffsView.addObject("allOptions", allOptions);
        return tariffsView;
    }

    @RequestMapping(value = "/showAllOptions", method = RequestMethod.GET)
    public ModelAndView showAllOptions() {
        ModelAndView optionsView = new ModelAndView("admin/options");
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/showOptionDetails", method = RequestMethod.POST)
    public ModelAndView showOptionDetails(@RequestParam String optionName) {
        ModelAndView optionDetailsView = new ModelAndView("admin/optionDetails");
        OptionDTO option = operatorService.findOptionByName(optionName);
        optionDetailsView.addObject("currentOption", option.getName());
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        List<OptionDTO> requiredOptions = operatorService.getRequiredOptions(optionName);
        List<OptionDTO> incompatibleOptions = operatorService.getIncompatibleOptions(optionName);
        optionDetailsView.addObject("requiredOptions", requiredOptions);
        optionDetailsView.addObject("incompatibleOptions", incompatibleOptions);
        allOptions = (List<OptionDTO>) operatorService.removeItem(allOptions, optionName);
        List<OptionDTO> addReqOptions = (List<OptionDTO>) operatorService.intercept(allOptions, requiredOptions);
        optionDetailsView.addObject("addReqOptions", addReqOptions);
        List<OptionDTO> addIncOptions = (List<OptionDTO>) operatorService.intercept(addReqOptions, incompatibleOptions);
        optionDetailsView.addObject("addIncOptions", addIncOptions);
        return optionDetailsView;
    }

    @RequestMapping(value = "/showTariffDetails", method = RequestMethod.POST)
    public ModelAndView showTariffDetails(@RequestParam String tariffName) {
        ModelAndView tariffDetailsView = new ModelAndView("admin/tariffDetails");
        List<OptionDTO> allOptions = operatorService.findAllOptions();
        List<OptionDTO> tariffOptions = operatorService.findOptionsByTariff(tariffName);
        allOptions = (List<OptionDTO>) operatorService.intercept(allOptions, tariffOptions);
        tariffOptions = operatorService.checkUniqueValues(tariffOptions);
        tariffDetailsView.addObject("tariff", tariffName);
        tariffDetailsView.addObject("tariffOptions", tariffOptions);
        tariffDetailsView.addObject("allOptions", allOptions);
        return tariffDetailsView;
    }

    @RequestMapping(value = "/showClient", method = RequestMethod.POST)
    public ModelAndView showClient(@RequestParam String number) {
        return prepareAccount(number);
    }

    @RequestMapping(value = "/assignNewContract", method = RequestMethod.GET)
    public ModelAndView assignNewContract() {
        return prepareAssignNewContract();
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
        ModelAndView notFoundView = new ModelAndView("admin/search");
        String notFound = "Not found";
        notFoundView.addObject("notFound", notFound);
        notFoundView.addObject("searchNumber", searchNumber);
        return notFoundView;
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

        operatorService.createNewClient(firstName, lastName, dateOfBirth, address, passport,
                email, password, selectedNumber, selectedTariff);
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

    @RequestMapping(value = "/addTariffOptions", method = RequestMethod.POST)
    public ModelAndView addTariffOptions(@RequestParam String tariffName,
                                     @RequestParam(value = "selectedOptions[]",
                                             required = false) String[] selectedOptions) {
        operatorService.addTariffOptions(tariffName, selectedOptions);
        return showTariffDetails(tariffName);
    }

    @RequestMapping(value = "/addRequiredOptions", method = RequestMethod.POST)
    public ModelAndView addRequiredOptions(@RequestParam String optionName,
                                         @RequestParam(value = "selectedOptions[]",
                                                 required = false) String[] selectedOptions) {
        operatorService.addRequiredOptions(optionName, selectedOptions);
        return showOptionDetails(optionName);
    }

    @RequestMapping(value = "/addIncompatibleOption", method = RequestMethod.POST)
    public ModelAndView addIncompatibleOption(@RequestParam String optionName,
                                           @RequestParam String incOption) {
        operatorService.addIncompatibleOption(optionName, incOption);
        return showOptionDetails(optionName);
    }

    @RequestMapping(value = "/removeRequiredOption", method = RequestMethod.POST)
    public ModelAndView removeRequiredOption(@RequestParam String currentOption,
                                           @RequestParam String optionToRemove) {
        operatorService.removeRequiredOption(currentOption, optionToRemove);
        return showOptionDetails(currentOption);
    }

    @RequestMapping(value = "/removeIncompatibleOption", method = RequestMethod.POST)
    public ModelAndView removeIncompatibleOption(@RequestParam String currentOption,
                                              @RequestParam String optionToRemove) {
        operatorService.removeIncompatibleOption(currentOption, optionToRemove);
        return showOptionDetails(currentOption);
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

    @RequestMapping(value = "/removeOptionFromTariff", method = RequestMethod.POST)
    public ModelAndView removeOptionFromTariff(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        String tariffName = requestParams.get("tariffName");
        operatorService.removeOptionFromTariff(optionName, tariffName);
        return showTariffDetails(tariffName);
    }

    @RequestMapping(value = "/setTariff", method = RequestMethod.POST)
    public ModelAndView setTariff(@RequestParam Map<String, String> requestParams) {
        String selectedTariff = requestParams.get("selectedTariff");
        String contractNumber = requestParams.get("number");
        TariffDTO tariff = operatorService.findTariffByName(selectedTariff);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
       List<OptionDTO> unacceptableOptions = operatorService.checkTariffOptions(selectedTariff, contractNumber);
        if (unacceptableOptions.size() > 0){
            ModelAndView accountView = prepareAccount(contractNumber);
            String setTariffError = "true";
            accountView.addObject("setTariffError", setTariffError);
            accountView.addObject("selectedTariff", selectedTariff);
            accountView.addObject("unacceptableOptions", unacceptableOptions);
            return accountView;
        }
        operatorService.setTariff(contract, tariff);
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/addMoreOptions", method = RequestMethod.POST)
    public ModelAndView addMoreOptions(@RequestParam String number,
                                       @RequestParam(value = "selectedOptions[]",
                                               required = false) String[] selectedOptions) {
        ContractDTO contract = operatorService.findContractByNumber(number);
        List<OptionDTO> chosenOptions = operatorService.prepareOptions(selectedOptions);
        List<OptionDTO> incompatibleOptions = operatorService.checkOptionsCompatibility(chosenOptions);
        if (incompatibleOptions.size() > 0){
            ModelAndView assignNewContractView = prepareAccount(number);
            String setOptionError = "true";
            assignNewContractView.addObject("setOptionError", setOptionError);
            assignNewContractView.addObject("incompatibleOptions", incompatibleOptions);
            return assignNewContractView;
        }
        operatorService.addOptions(contract, chosenOptions);
        return prepareAccount(number);
    }
}