package com.tsystems.javaschool.facade.controllers;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.OptionDTO;
import com.tsystems.javaschool.facade.dto.TariffDTO;
import com.tsystems.javaschool.service.services.ClientService;
import com.tsystems.javaschool.service.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * ClientController.
 */
@Controller
@SessionAttributes({"currentContract", "optionsInBasket", "tariffInBasket"})
@RequestMapping("/user")
public class ClientController {

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    private ModelAndView prepareJSP(String contractNumber, String path) {
        ClientDTO client = operatorService.findClientByNumber(contractNumber);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        TariffDTO tariff = contract.getTariff();
        List<OptionDTO> options = contract.getOptions();
        ModelAndView contractView = new ModelAndView(path);
        contractView.addObject("contract", contract);
        contractView.addObject("client", client);
        contractView.addObject("tariff", tariff);
        contractView.addObject("options", options);
        return contractView;
    }

    @RequestMapping(value = "/showPersonal", method = RequestMethod.GET)
    public ModelAndView showPersonal(@ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        String path = "client";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showContract", method = RequestMethod.GET)
    public ModelAndView showContract(@ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/changeContract", method = RequestMethod.POST)
    public ModelAndView changeContract(@RequestParam String contractNumber,
                                       @ModelAttribute("currentContract") List<ContractDTO> contracts) {
        contracts.clear();
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        contracts.add(contract);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showTariffs", method = RequestMethod.GET)
    public ModelAndView showTariffs(@ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        String path = "client/tariff";
        List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
        ModelAndView tariffsView = prepareJSP(contractNumber, path);
        tariffsView.addObject("tariffs", tariffsToSelect);
        return tariffsView;
    }

    @RequestMapping(value = "/showOptions", method = RequestMethod.GET)
    public ModelAndView showOptions(@ModelAttribute("currentContract") List<ContractDTO> contracts,
                                    @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket) {
        String contractNumber = contracts.get(0).getNumber();
        List<OptionDTO> tariffOptions = operatorService.findOptionsByTariff(contracts.get(0).getTariff().getName());
        List<OptionDTO> contractOptions = contracts.get(0).getOptions();
        List<OptionDTO> optionsToExclude = new ArrayList<>(contractOptions);
        optionsToExclude.addAll(optionsInBasket);
        List<OptionDTO> optionsToSelect = operatorService.intercept(tariffOptions, optionsToExclude);
        String path = "client/option";
        ModelAndView optionsView = prepareJSP(contractNumber, path);
        optionsView.addObject("allOptions", optionsToSelect);
        return optionsView;
    }

    @RequestMapping(value = "/showBasket", method = RequestMethod.GET)
    public ModelAndView showBasket(@ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {
        ModelAndView basket = new ModelAndView("client/basket");
        String setTariffError = "false";
        basket.addObject("tariffInBasket", tariffInBasket);
        basket.addObject("setTariffError", setTariffError);
        return basket;
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public ModelAndView changeTariff(@RequestParam String tariffName,
                                     @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {
        tariffInBasket.clear();
        tariffInBasket.add(0, operatorService.findTariffByName(tariffName));
        return showBasket(tariffInBasket);
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public ModelAndView addOption(@RequestParam String optionName, @ModelAttribute("currentContract") List<ContractDTO> contracts,
                                  @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket) {
        OptionDTO option = operatorService.findOptionByName(optionName);
        optionsInBasket.add(option);
        List<OptionDTO> requiredOptions = operatorService.getRequiredOptions(optionName);
        optionsInBasket.addAll(requiredOptions);
        return showOptions(contracts, optionsInBasket);
    }

    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public ModelAndView removeOption(@RequestParam String optionName, @ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        clientService.removeOption(contractNumber, optionName);
        contracts.clear();
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        contracts.add(contract);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/clearBasket", method = RequestMethod.POST)
    public ModelAndView removeTariffFromBasket(@ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket,
                                               @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket) {
        tariffInBasket.clear();
        optionsInBasket.clear();
        return showBasket(tariffInBasket);
    }

    @RequestMapping(value = "/lockContractByClient", method = RequestMethod.GET)
    public ModelAndView lockContractByClient(@ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        clientService.lockContract(contractNumber);
        contracts.get(0).setBlockedByClient(true);
        String path = "client/contractLockedByClient";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/unlockContractByClient", method = RequestMethod.GET)
    public ModelAndView unlockContractByClient(@ModelAttribute("currentContract") List<ContractDTO> contracts) {
        String contractNumber = contracts.get(0).getNumber();
        clientService.unLockContract(contractNumber);
        contracts.get(0).setBlockedByClient(false);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/buyItems", method = RequestMethod.POST)
    public ModelAndView buyItems(@ModelAttribute("currentContract") List<ContractDTO> contracts,
                                 @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket,
                                 @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {
        String contractNumber = contracts.get(0).getNumber();
        List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
        if (tariffInBasket.size() > 0) {
            TariffDTO tariff = tariffInBasket.get(0);
            List<OptionDTO> tariffOptions = operatorService.findOptionsByTariff(tariff.getName());
            List<OptionDTO> contractOptions = contracts.get(0).getOptions();
            List<OptionDTO> optionsToExclude = new ArrayList<>(contractOptions);
            optionsToExclude.addAll(optionsInBasket);
            List<OptionDTO> unacceptableOptions = operatorService.intercept(optionsToExclude, tariffOptions);
            if (unacceptableOptions.size() > 0){
                ModelAndView basket = showBasket(tariffInBasket);
                String setTariffError = "true";
                basket.addObject("setTariffError", setTariffError);
                basket.addObject("selectedTariff", tariff.getName());
                basket.addObject("unacceptableOptions", unacceptableOptions);
                return basket;
            }
            tariffsToSelect.add(contracts.get(0).getTariff());
            operatorService.setTariff(contracts.get(0), tariff);
            tariffInBasket.clear();
        }
        if (optionsInBasket.size() > 0) {
            operatorService.addOptions(contracts.get(0), optionsInBasket);
            optionsInBasket.clear();
        }
        contracts.clear();
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        contracts.add(contract);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }
}