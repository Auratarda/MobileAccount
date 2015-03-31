package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.*;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * LoginServlet.
 */
@Controller
@SessionAttributes({"currentContract", "optionsInBasket", "tariffInBasket", "tariffsToSelect", "optionsToSelect"})
@RequestMapping("/main")
public class LoginController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam Map<String, String> requestParams, HttpSession httpSession) {
        logger.info("Starting LoginController.");
        String email = requestParams.get("email");
        String password = requestParams.get("password");
        try {
            ClientDTO client = clientService.login(email, password);
            logger.debug("client = " + client);
            List<RoleDTO> roles = client.getRoles();
            for (RoleDTO role : roles) {
                if (role.getRole().equals("ADMIN")) {
                    ModelAndView adminView = new ModelAndView("admin");
                    adminView.addObject("role", roles);
                    return adminView;
                }
                if (role.getRole().equals("CLIENT")) {
                    ModelAndView clientView = new ModelAndView("client");
                    ContractDTO contract = client.getContracts().get(0);
                    List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
                    TariffDTO contractTariff = contract.getTariff();
                    Iterator iter = tariffsToSelect.iterator();
                    while (iter.hasNext()) {
                        TariffDTO tariff = (TariffDTO) iter.next();
                            if (tariff.getName().equals(contractTariff.getName())) iter.remove();
                    }
                    List<OptionDTO> optionsToSelect = operatorService.findAllOptions();
                    List<OptionDTO> contractOptions = contract.getOptions();
                    iter = optionsToSelect.iterator();
                    while (iter.hasNext()) {
                        OptionDTO option = (OptionDTO) iter.next();
                        for (OptionDTO contractOption : contractOptions) {
                            if (option.getName().equals(contractOption.getName())) iter.remove();
                        }
                    }
                    List<OptionDTO> optionsInBasket = new ArrayList<>();
                    List<OptionDTO> tariffInBasket = new ArrayList<>();
                    httpSession.setAttribute("tariffInBasket", tariffInBasket);
                    httpSession.setAttribute("optionsInBasket", optionsInBasket);
                    httpSession.setAttribute("currentContract", contract);
                    httpSession.setAttribute("tariffsToSelect", tariffsToSelect);
                    httpSession.setAttribute("optionsToSelect", optionsToSelect);
                    clientView.addObject("contract", contract);
                    clientView.addObject("client", client);
                    clientView.addObject("role", roles);
                    if (contract.getBlockedByClient()){
                        return new ModelAndView("client/contractLockedByClient");
                    }
                    return clientView;
                }
            }
        } catch (ClientNotFoundException e) {
            logger.debug("Client is not found");
            return new ModelAndView("loginError");
        }
        return null;
    }
}
