package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.*;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * LoginServlet.
 */
@Controller
@SessionAttributes({"currentContract"})
@RequestMapping("/main")
@Component
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
                    httpSession.setAttribute("currentContract", contract);
                    clientView.addObject("contract", contract);
                    clientView.addObject("client", client);
                    clientView.addObject("role", roles);
                    return clientView;
                }
            }
        } catch (ClientNotFoundException e) {
            logger.debug("Client is not found");
            ModelAndView errorView = new ModelAndView("loginError");
            return errorView;
        }
        return null;
    }

    @RequestMapping(value = "/showContract", method = RequestMethod.GET)
    public ModelAndView showContract(@ModelAttribute("currentContract") ContractDTO contract) {
        String contractNumber = contract.getNumber();
        String path = "client/contract";
        return prepareJSP(contractNumber, path);
    }

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
}
