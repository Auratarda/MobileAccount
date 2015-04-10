package com.tsystems.javaschool.web.controllers;

import com.tsystems.javaschool.facade.OperatorFacade;
import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
import com.tsystems.javaschool.service.exceptions.ClientNotFoundException;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Qualifier("operatorFacade")
    private OperatorFacade operatorFacade;

    private static final Logger log = Logger.getLogger(AdminController.class);

    private ModelAndView prepareAccount(String contractNumber) {
        log.info("Creating account");
        ClientDTO client = operatorFacade.findClientByNumber(contractNumber);
        ContractDTO contract = operatorFacade.findContractByNumber(contractNumber);
        TariffDTO tariff = contract.getTariff();
        String status = "Не заблокирован";
        if (contract.getBlockedByClient()) status = "Заблокирован клиентом";
        if (contract.getBlockedByOperator()) status = "Заблокирован оператором";
        List<TariffDTO> allTariffs = operatorFacade.findAllTariffs();
        List<OptionDTO> tariffOptions = operatorFacade.findOptionsByTariff(tariff.getName());
        List<OptionDTO> contractOptions = contract.getOptions();
        List<OptionDTO> optionsToSelect = new ArrayList<>(tariffOptions);
        optionsToSelect.removeAll(contractOptions);
        String tariffOptionError = "false";
        String incOptionError = "false";
        String reqOptionError = "false";
        ModelAndView accountView = new ModelAndView("admin/account");
        accountView.addObject("contract", contract);
        accountView.addObject("client", client);
        accountView.addObject("tariff", tariff);
        accountView.addObject("options", contractOptions);
        accountView.addObject("status", status);
        accountView.addObject("tariffs", allTariffs);
        accountView.addObject("allOptions", optionsToSelect);
        accountView.addObject("tariffOptionError", tariffOptionError);
        accountView.addObject("incOptionError", incOptionError);
        accountView.addObject("reqOptionError", reqOptionError);
        return accountView;
    }

    private ModelAndView prepareContracts() {
        ModelAndView contractsView = new ModelAndView("admin");
        List<ContractDTO> allContracts = operatorFacade.findAllContracts();
        contractsView.addObject("allContracts", allContracts);
        return contractsView;
    }

    private ModelAndView prepareAssignNewContract() {
        ModelAndView assignContractView = new ModelAndView("admin/assignContract");
        List<ContractDTO> freeContracts = operatorFacade.findFreeContracts();
        List<TariffDTO> allTariffs = operatorFacade.findAllTariffs();
        List<OptionDTO> allOptions = operatorFacade.findAllOptions();
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
        List<ContractDTO> allContracts = operatorFacade.findAllContracts();
        adminView.addObject("allContracts", allContracts);
        return adminView;
    }

    @RequestMapping(value = "/showAllTariffs", method = RequestMethod.GET)
    public ModelAndView showAllTariffs() {
        ModelAndView tariffsView = new ModelAndView("admin/tariffs");
        List<TariffDTO> allTariffs = operatorFacade.findAllTariffs();
        List<OptionDTO> allOptions = operatorFacade.findAllOptions();
        tariffsView.addObject("allTariffs", allTariffs);
        tariffsView.addObject("allOptions", allOptions);
        return tariffsView;
    }

    @RequestMapping(value = "/showAllOptions", method = RequestMethod.GET)
    public ModelAndView showAllOptions() {
        ModelAndView optionsView = new ModelAndView("admin/options");
        List<OptionDTO> allOptions = operatorFacade.findAllOptions();
        optionsView.addObject("allOptions", allOptions);
        return optionsView;
    }

    @RequestMapping(value = "/showOptionDetails", method = RequestMethod.POST)
    public ModelAndView showOptionDetails(@RequestParam String optionName) {
        ModelAndView optionDetailsView = new ModelAndView("admin/optionDetails");
        OptionDTO option = operatorFacade.findOptionByName(optionName);
        optionDetailsView.addObject("currentOption", option.getName());
        List<OptionDTO> allOptions = operatorFacade.findAllOptions();
        List<OptionDTO> requiredOptions = operatorFacade.getRequiredOptions(optionName);
        List<OptionDTO> incompatibleOptions = operatorFacade.getIncompatibleOptions(optionName);
        optionDetailsView.addObject("requiredOptions", requiredOptions);
        optionDetailsView.addObject("incompatibleOptions", incompatibleOptions);
        allOptions = (List<OptionDTO>) operatorFacade.removeItem(allOptions, optionName);
        List<OptionDTO> addReqOptions = (List<OptionDTO>) operatorFacade.intersect(allOptions, requiredOptions);
        optionDetailsView.addObject("addReqOptions", addReqOptions);
        List<OptionDTO> addIncOptions = (List<OptionDTO>) operatorFacade.intersect(addReqOptions, incompatibleOptions);
        optionDetailsView.addObject("addIncOptions", addIncOptions);
        return optionDetailsView;
    }

    @RequestMapping(value = "/showTariffDetails", method = RequestMethod.POST)
    public ModelAndView showTariffDetails(@RequestParam String tariffName) {
        ModelAndView tariffDetailsView = new ModelAndView("admin/tariffDetails");
        List<OptionDTO> allOptions = operatorFacade.findAllOptions();
        List<OptionDTO> tariffOptions = operatorFacade.findOptionsByTariff(tariffName);
        allOptions = (List<OptionDTO>) operatorFacade.intersect(allOptions, tariffOptions);
        tariffOptions = operatorFacade.checkUniqueValues(tariffOptions);
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
        List<ContractDTO> contracts = operatorFacade.findAllContracts();
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

        operatorFacade.createNewClient(firstName, lastName, dateOfBirth, address, passport,
                email, password, selectedNumber, selectedTariff);
        return showClient(selectedNumber);
    }

    @RequestMapping(value = "/removeClient", method = RequestMethod.POST)
    public ModelAndView removeClient(@RequestParam Map<String, String> requestParams) {
        String contractToRemove = requestParams.get("contractToRemove");
        ClientDTO clientToRemove = operatorFacade.findClientByNumber(contractToRemove);
        List<ContractDTO> freeContracts = operatorFacade.findFreeContracts();
        List<ContractDTO> contractsToFree = clientToRemove.getContracts();
        for (ContractDTO contract : contractsToFree) {
            freeContracts.add(contract);
        }
        try {
            operatorFacade.removeClient(clientToRemove);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        return prepareContracts();
    }

    @RequestMapping(value = "/lockContractByOperator", method = RequestMethod.POST)
    public ModelAndView lockContractByOperator(@RequestParam String number) {
        ContractDTO contractToLock = operatorFacade.findContractByNumber(number);
        operatorFacade.lockContractByOperator(number);
        ModelAndView accountView = prepareAccount(number);
        String status = "Заблокирован оператором";
        accountView.addObject("status", status);
        return accountView;
    }

    @RequestMapping(value = "/unlockContractByOperator", method = RequestMethod.POST)
    public ModelAndView unlockContractByOperator(@RequestParam String number) {
        ContractDTO contractToUnlock = operatorFacade.findContractByNumber(number);
        operatorFacade.unlockContractByOperator(number);
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
        operatorFacade.createNewTariff(tariffName, tariffPrice);
        return showAllTariffs();
    }

    @RequestMapping(value = "/addTariffOptions", method = RequestMethod.POST)
    public ModelAndView addTariffOptions(@RequestParam String tariffName,
                                     @RequestParam(value = "selectedOptions[]",
                                             required = false) String[] selectedOptions) {
        operatorFacade.addTariffOptions(tariffName, selectedOptions);
        return showTariffDetails(tariffName);
    }

    @RequestMapping(value = "/addRequiredOptions", method = RequestMethod.POST)
    public ModelAndView addRequiredOptions(@RequestParam String optionName,
                                         @RequestParam(value = "selectedOptions[]",
                                                 required = false) String[] selectedOptions) {
        operatorFacade.addRequiredOptions(optionName, selectedOptions);
        return showOptionDetails(optionName);
    }

    @RequestMapping(value = "/addIncompatibleOption", method = RequestMethod.POST)
    public ModelAndView addIncompatibleOption(@RequestParam String optionName,
                                           @RequestParam String incOption) {
        operatorFacade.addIncompatibleOption(optionName, incOption);
        return showOptionDetails(optionName);
    }

    @RequestMapping(value = "/removeRequiredOption", method = RequestMethod.POST)
    public ModelAndView removeRequiredOption(@RequestParam String currentOption,
                                           @RequestParam String optionToRemove) {
        operatorFacade.removeRequiredOption(currentOption, optionToRemove);
        return showOptionDetails(currentOption);
    }

    @RequestMapping(value = "/removeIncompatibleOption", method = RequestMethod.POST)
    public ModelAndView removeIncompatibleOption(@RequestParam String currentOption,
                                              @RequestParam String optionToRemove) {
        operatorFacade.removeIncompatibleOption(currentOption, optionToRemove);
        return showOptionDetails(currentOption);
    }

    @RequestMapping(value = "/removeTariff", method = RequestMethod.POST)
    public ModelAndView removeTariff(@RequestParam String tariffName) {
        TariffDTO tariffToRemove = operatorFacade.findTariffByName(tariffName);
        operatorFacade.removeTariff(tariffToRemove);
        return showAllTariffs();
    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.POST)
    public ModelAndView addNewOption(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        Long optionPrice = Long.parseLong(requestParams.get("optionPrice"));
        Long connectionCost = Long.parseLong(requestParams.get("connectionCost"));
        operatorFacade.createNewOption(optionName, optionPrice, connectionCost);
        return showAllOptions();
    }

    @RequestMapping(value = "/dropOption", method = RequestMethod.POST)
    public ModelAndView dropOption(@RequestParam String optionName) {
        operatorFacade.dropOption(optionName);
        return showAllOptions();
    }

    @RequestMapping(value = "/removeOptionFromContract", method = RequestMethod.POST)
    public ModelAndView removeOptionFromContract(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        String contractNumber = requestParams.get("number");
        String [] requiredOptions = operatorFacade.removeOption(contractNumber, optionName);
        if (requiredOptions.length > 0){
            ModelAndView assignNewContractView = prepareAccount(contractNumber);
            String setReqOptionError = "true";
            assignNewContractView.addObject("setReqOptionError", setReqOptionError);
            assignNewContractView.addObject("requiredOptions", requiredOptions);
            return assignNewContractView;
        }
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/removeOptionFromTariff", method = RequestMethod.POST)
    public ModelAndView removeOptionFromTariff(@RequestParam Map<String, String> requestParams) {
        String optionName = requestParams.get("optionName");
        String tariffName = requestParams.get("tariffName");
        operatorFacade.removeOptionFromTariff(optionName, tariffName);
        return showTariffDetails(tariffName);
    }

    @RequestMapping(value = "/setTariff", method = RequestMethod.POST)
    public ModelAndView setTariff(@RequestParam Map<String, String> requestParams) {
        String selectedTariff = requestParams.get("selectedTariff");
        String contractNumber = requestParams.get("number");
        String[] unacceptableOptions = operatorFacade.changeTariff(contractNumber, selectedTariff);
        if (unacceptableOptions.length > 0){
            ModelAndView accountView = prepareAccount(contractNumber);
            String setTariffError = "true";
            accountView.addObject("setTariffError", setTariffError);
            accountView.addObject("selectedTariff", selectedTariff);
            accountView.addObject("unacceptableOptions", unacceptableOptions);
            return accountView;
        }
        return prepareAccount(contractNumber);
    }

    @RequestMapping(value = "/addMoreOptions", method = RequestMethod.POST)
    public ModelAndView addMoreOptions(@RequestParam String number,
                                       @RequestParam(value = "selectedOptions[]",
                                               required = false) String[] selectedOptions) {
        String[] incompatibleOptions = operatorFacade.addOptions(number, selectedOptions);
        if (incompatibleOptions.length > 0){
            ModelAndView assignNewContractView = prepareAccount(number);
            String setOptionError = "true";
            assignNewContractView.addObject("setOptionError", setOptionError);
            assignNewContractView.addObject("incompatibleOptions", incompatibleOptions);
            return assignNewContractView;
        }
        return prepareAccount(number);
    }
}