package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.ContractDTO;
import com.tsystems.javaschool.dto.OptionDTO;
import com.tsystems.javaschool.dto.TariffDTO;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;

/**
 * ClientController.
 */
@Controller
@SessionAttributes({"currentContract", "optionsInBasket", "tariffInBasket", "tariffsToSelect", "optionsToSelect"})
@RequestMapping("/main")
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
    public ModelAndView showPersonal(@ModelAttribute("currentContract") ContractDTO contract) {
        String contractNumber = contract.getNumber();
        String path = "client";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showContract", method = RequestMethod.GET)
    public ModelAndView showContract(@ModelAttribute("currentContract") ContractDTO contract) {
        String contractNumber = contract.getNumber();
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showTariffs", method = RequestMethod.GET)
    public ModelAndView showTariffs(@ModelAttribute("currentContract") ContractDTO contract,
                                    @ModelAttribute("tariffsToSelect") List<TariffDTO> tariffsToSelect) {
        String contractNumber = contract.getNumber();
        String path = "client/tariff";
        ModelAndView tariffsView = prepareJSP(contractNumber, path);
        tariffsView.addObject("tariffs", tariffsToSelect);
        return tariffsView;
    }

    @RequestMapping(value = "/showOptions", method = RequestMethod.GET)
    public ModelAndView showOptions(@ModelAttribute("currentContract") ContractDTO contract,
                                    @ModelAttribute("optionsToSelect") List<OptionDTO> optionsToSelect,
                                    @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket) {
        String contractNumber = contract.getNumber();
        Iterator iter = optionsToSelect.iterator();
        while (iter.hasNext()) {
            OptionDTO option = (OptionDTO) iter.next();
            for (OptionDTO optionInBasket : optionsInBasket) {
                if (option.getName().equals(optionInBasket.getName())) iter.remove();
            }
        }
        String path = "client/option";
        ModelAndView optionsView = prepareJSP(contractNumber, path);
        optionsView.addObject("allOptions", optionsToSelect);
        return optionsView;
    }

    @RequestMapping(value = "/showBasket", method = RequestMethod.GET)
    public ModelAndView showBasket(@ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {
        ModelAndView basket = new ModelAndView("client/basket");
        basket.addObject("tariffInBasket", tariffInBasket);
        return basket;
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public ModelAndView changeTariff(@RequestParam String tariffName,
                                     @ModelAttribute("currentContract") ContractDTO contract,
                                     @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket,
                                     @ModelAttribute("tariffsToSelect") List<TariffDTO> tariffsToSelect) {
        tariffInBasket.add(0, operatorService.findTariffByName(tariffName));
        Iterator iter = tariffsToSelect.iterator();
        while (iter.hasNext()) {
            TariffDTO tariff = (TariffDTO) iter.next();
            if (tariff.getName().equals(tariffInBasket.get(0).getName())) iter.remove();
        }
        return showBasket(tariffInBasket);
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public ModelAndView addOption(@RequestParam String optionName, @ModelAttribute("currentContract") ContractDTO contract,
                                  @ModelAttribute("optionsToSelect") List<OptionDTO> optionsToSelect,
                                  @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket) {
        OptionDTO option = operatorService.findOptionByName(optionName);
        optionsInBasket.add(option);
        return showOptions(contract, optionsToSelect, optionsInBasket);
    }

    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public ModelAndView removeOption(@RequestParam String optionName, @ModelAttribute("currentContract") ContractDTO contract,
                                     @ModelAttribute("optionsToSelect") List<OptionDTO> optionsToSelect) {
        String contractNumber = contract.getNumber();
        optionsToSelect.add(operatorService.findOptionByName(optionName));
        clientService.removeOption(contractNumber, optionName);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/removeOptionFromBasket", method = RequestMethod.POST)
    public ModelAndView removeOptionFromBasket(@RequestParam String optionName,
                                               @ModelAttribute("currentContract") ContractDTO contract,
                                               @ModelAttribute("optionsToSelect") List<OptionDTO> optionsToSelect,
                                               @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket,
                                               @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {
        OptionDTO optionToRemove = operatorService.findOptionByName(optionName);
        optionsToSelect.add(optionToRemove);
        Iterator iter = optionsInBasket.iterator();
        while (iter.hasNext()) {
            OptionDTO option = (OptionDTO) iter.next();
            if (option.getName().equals(optionToRemove.getName())) iter.remove();
        }
        return showBasket(tariffInBasket);
    }

    @RequestMapping(value = "/removeTariffFromBasket", method = RequestMethod.POST)
    public ModelAndView removeTariffFromBasket(@ModelAttribute("currentContract") ContractDTO contract,
                                               @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket) {

        return showBasket(tariffInBasket);
    }

    @RequestMapping(value = "/lockContractByClient", method = RequestMethod.GET)
    public ModelAndView lockContractByClient(@ModelAttribute("currentContract") ContractDTO contract) {
        String contractNumber = contract.getNumber();
        clientService.lockContract(contractNumber);
        contract.setBlockedByClient(true);
        String path = "client/contractLockedByClient";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/unlockContractByClient", method = RequestMethod.GET)
    public ModelAndView unlockContractByClient(@ModelAttribute("currentContract") ContractDTO contract) {
        String contractNumber = contract.getNumber();
        clientService.unLockContract(contractNumber);
        contract.setBlockedByClient(false);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/buyItems", method = RequestMethod.POST)
    public ModelAndView buyItems(@ModelAttribute("currentContract") ContractDTO contract,
                                 @ModelAttribute("optionsInBasket") List<OptionDTO> optionsInBasket,
                                 @ModelAttribute("tariffInBasket") List<TariffDTO> tariffInBasket,
                                 @ModelAttribute("tariffsToSelect") List<TariffDTO> tariffsToSelect) {
        String contractNumber = contract.getNumber();
        if (optionsInBasket.size() > 0) {
            operatorService.addOptions(contract, optionsInBasket);
            optionsInBasket.clear();
        }
        if (tariffInBasket.size() > 0) {
            tariffsToSelect.add(contract.getTariff());
            operatorService.setTariff(contract, tariffInBasket.get(0));
            tariffInBasket.clear();
        }
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }
}