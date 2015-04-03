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
import java.util.Iterator;
import java.util.List;

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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "../../index";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(HttpSession httpSession){
        Collection<GrantedAuthority> grantedAuthorities =
                (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication()
                        .getAuthorities();
        String authority = null;
        for (GrantedAuthority ga : grantedAuthorities){
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

        ModelAndView clientView = new ModelAndView("client");
        ContractDTO contract = client.getContracts().get(0);
        List<TariffDTO> tariffsToSelect = operatorService.findAllTariffs();
        List<OptionDTO> optionsToSelect = operatorService.findAllOptions();
        List<OptionDTO> contractOptions = contract.getOptions();
        Iterator iter = optionsToSelect.iterator();
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
        if (contract.getBlockedByClient()){
            return new ModelAndView("client/contractLockedByClient");
        }
        return clientView;
    }
}
