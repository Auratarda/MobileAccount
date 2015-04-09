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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * LoginServlet.
 */
@Controller
@SessionAttributes({"currentContract", "optionsInBasket", "tariffInBasket"})
@RequestMapping("/main")
public class LoginController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    @Qualifier("operatorService")
    private OperatorService operatorService;
    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "../../index";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(HttpSession httpSession) {
        Collection<GrantedAuthority> grantedAuthorities =
                (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication()
                        .getAuthorities();
        String authority = null;
        for (GrantedAuthority ga : grantedAuthorities) {
            if ("ADMIN".equals(ga.getAuthority())) {
                authority = ga.getAuthority();
                ModelAndView adminView = new ModelAndView("admin");
                List<ContractDTO> allContracts = operatorService.findAllContracts();
                adminView.addObject("allContracts", allContracts);
                return adminView;
            }
            if ("CLIENT".equals(ga.getAuthority())) {
                authority = ga.getAuthority();
            }
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String email = userDetails.getUsername();
        String password = userDetails.getPassword();
        ClientDTO client = null;
        try {
            client = clientService.login(email, password);
        } catch (ClientNotFoundException e) {
            logger.debug("Client is not found");
            return new ModelAndView(index());
        }
        String path = "client";
        List<ContractDTO> contracts = new ArrayList<>();
        contracts.add(client.getContracts().get(0));
        if (contracts.get(0).getBlockedByClient()) {
            path = "client/contractLockedByClient";
        }
        if (contracts.get(0).getBlockedByOperator()) {
            path = "client/contractLockedByOperator";
        }
        ModelAndView clientView = new ModelAndView(path);
        clientView.addObject("contract", contracts.get(0));
        List<OptionDTO> optionsInBasket = new ArrayList<>();
        List<TariffDTO> tariffInBasket = new ArrayList<>();
        httpSession.setAttribute("tariffInBasket", tariffInBasket);
        httpSession.setAttribute("optionsInBasket", optionsInBasket);
        httpSession.setAttribute("currentContract", contracts);
        clientView.addObject("client", client);
        return clientView;
    }
}
