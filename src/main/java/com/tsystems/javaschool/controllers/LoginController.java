package com.tsystems.javaschool.controllers;

import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.ContractDTO;
import com.tsystems.javaschool.dto.RoleDTO;
import com.tsystems.javaschool.exceptions.ClientNotFoundException;
import com.tsystems.javaschool.services.ClientService;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam Map<String, String> requestParams) {
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
}
