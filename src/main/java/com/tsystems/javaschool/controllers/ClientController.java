package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.ContractDTO;
import com.tsystems.javaschool.dto.OptionDTO;
import com.tsystems.javaschool.dto.TariffDTO;
import com.tsystems.javaschool.exceptions.ContractIsBlockedException;
import com.tsystems.javaschool.exceptions.IncompatibleOptionException;
import com.tsystems.javaschool.exceptions.RequiredOptionException;
import com.tsystems.javaschool.exceptions.TariffNotSupportedOptionException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClientController.
 */
@Controller
@RequestMapping("/main")
@Component
public class ClientController {
    private final static Logger logger = Logger.getLogger(ClientController.class);

    @Resource(name = "operatorService")
    private OperatorService operatorService;
    @Resource(name = "clientService")
    private ClientService clientService;

    @RequestMapping(value = "/showPersonal", method = RequestMethod.GET)
    public String showPersonal() {
        return "client";
    }

    @RequestMapping(value = "/showContract", method = RequestMethod.GET)
    public String showContract() {
        return "contract";
    }

    @RequestMapping(value = "/showTariffs", method = RequestMethod.GET)
    public String showTariffs(Model model) {
        List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
        ContractDTO contract = (ContractDTO) model.asMap().get("contract");
        TariffDTO currentTarrif = contract.getTariff();
        tariffsToSelect.remove(currentTarrif);
        model.addAttribute("tariffsToSelect", tariffsToSelect);
        return "tariff";
    }

    @RequestMapping(value = "/showOptions", method = RequestMethod.GET)
    public String showOptions(Model model) {
        List<OptionDTO> optionsToSelect = operatorService.findAllOptions();
        model.addAttribute("optionsToSelect", optionsToSelect);
        return "option";
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public String changeTariff(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contractNumber");
        String tariffName = requestParams.get("tariffName");
        try {
            clientService.changeTariff(contractNumber, tariffName);
        } catch (TariffNotSupportedOptionException e) {
            e.printStackTrace();
        } catch (ContractIsBlockedException e) {
            e.printStackTrace();
        }
        return "contract";
    }

    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    public String addOption(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contractNumber");
        String optionName = requestParams.get("optionName");
        try {
            clientService.addOption(contractNumber, optionName);
        } catch (IncompatibleOptionException e) {
            e.printStackTrace();
        } catch (RequiredOptionException e) {
            e.printStackTrace();
        } catch (ContractIsBlockedException e) {
            e.printStackTrace();
        }
        return "contract";
    }

    @RequestMapping(value = "/removeOption", method = RequestMethod.POST)
    public String removeOption(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contractNumber");
        String optionName = requestParams.get("optionName");
        try {
            clientService.removeOption(contractNumber, optionName);
            return "contract";
        } catch (RequiredOptionException e) {
            e.printStackTrace();
        } catch (ContractIsBlockedException e) {
            e.printStackTrace();
        }
        return "contract";
    }

    @RequestMapping(value = "/lockContractByClient", method = RequestMethod.POST)
    public String lockContractByClient(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contractNumber");
        clientService.lockContract(contractNumber);
        return "contractLockedByClient";
    }

    @RequestMapping(value = "/unlockContractByClient", method = RequestMethod.POST)
    public String unlockContractByClient(@RequestParam Map<String, String> requestParams) {
        String contractNumber = requestParams.get("contractNumber");
        clientService.unLockContract(contractNumber);
        return "contract";
    }
}