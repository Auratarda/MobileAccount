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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClientController.
 */
@Controller
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

    @RequestMapping(value = "/showPersonal", method = RequestMethod.POST)
    public ModelAndView showPersonal(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String path = "client";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showContract", method = RequestMethod.POST)
    public ModelAndView showContract(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/showTariffs", method = RequestMethod.POST)
    public ModelAndView showTariffs(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String path = "client/tariff";
        ModelAndView tariffsView = prepareJSP(contractNumber, path);
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        TariffDTO tariffDTO = contract.getTariff();
        List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
        int index = -1;
        for (int i = 0; i < tariffsToSelect.size(); i++) {
            if (tariffsToSelect.get(i).getName().equals(tariffDTO.getName())) index = i;
        }
        tariffsToSelect.remove(index);
        tariffsView.addObject("tariffs", tariffsToSelect);
        return tariffsView;
    }

    @RequestMapping(value = "/showOptions", method = RequestMethod.POST)
    public ModelAndView showOptions(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        ContractDTO contract = operatorService.findContractByNumber(contractNumber);
        List<OptionDTO> currentOptions = contract.getOptions();
        List<OptionDTO> optionsToSelect = operatorService.findAllOptions();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < optionsToSelect.size(); i++) {
            for (OptionDTO currentOption : currentOptions) {
                if (optionsToSelect.get(i).getName().equals(currentOption.getName())) indexes.add(i);
            }
        }
        for (Integer index : indexes) {
            optionsToSelect.remove((int) index);
        }
        String path = "client/option";
        ModelAndView optionsView = prepareJSP(contractNumber, path);
        optionsView.addObject("allOptions", optionsToSelect);
        return optionsView;
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public ModelAndView changeTariff(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String tariffName = requestParams.get("tariffName");
        clientService.changeTariff(contractNumber, tariffName);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public ModelAndView addOption(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String optionName = requestParams.get("optionName");
        clientService.addOption(contractNumber, optionName);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public ModelAndView removeOption(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        String optionName = requestParams.get("optionName");
        clientService.removeOption(contractNumber, optionName);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/lockContractByClient", method = RequestMethod.POST)
    public ModelAndView lockContractByClient(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        clientService.lockContract(contractNumber);
        String path = "client/contractLockedByClient";
        return prepareJSP(contractNumber, path);
    }

    @RequestMapping(value = "/unlockContractByClient", method = RequestMethod.POST)
    public ModelAndView unlockContractByClient(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contract");
        clientService.unLockContract(contractNumber);
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }
}