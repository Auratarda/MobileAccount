package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.ContractDTO;
import com.tsystems.javaschool.dto.RoleDTO;
import com.tsystems.javaschool.dto.TariffDTO;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Map;

/**
 * LoginServlet.
 */
@Controller
@RequestMapping("/main")
@Component
public class LoginController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Resource(name = "operatorService")
    private OperatorService operatorService;
    @Resource(name = "clientService")
    private ClientService clientService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam Map<String, String> requestParams) {
        String email = requestParams.get("email");
        logger.debug("email = " + email);
        String password = requestParams.get("password");
        logger.debug("password = " + password);
        ModelAndView mav = new ModelAndView();
        try {
            ClientDTO client = clientService.login(email, password);
            logger.debug("client = " + client);
            mav.addObject("client", client);
            ContractDTO contract = client.getContracts().get(0);
            mav.addObject("contract", contract);

            List<RoleDTO> roles = client.getRoles();
            mav.addObject("role", roles);
            for (RoleDTO role : roles) {
                if (role.getRole().equals("ADMIN")) {
                    List<ContractDTO> contracts = operatorService.findAllContracts();
                    mav.addObject("contracts", contracts);
                    List<TariffDTO> tariffs = operatorService.findAllTariffs();
                    mav.addObject("tariffs", tariffs);
                    return "admin";
                }
                if (role.getRole().equals("CLIENT")) {
                    return "client";
                }
            }
        } catch (ClientNotFoundException e) {
            logger.debug("Client is not found");
            return "loginError.jsp";
        }
        return null;
    }
}
