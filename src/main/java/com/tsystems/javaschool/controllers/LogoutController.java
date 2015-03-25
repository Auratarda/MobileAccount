package com.tsystems.javaschool.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * LogoutController.
 */
@Controller
@RequestMapping("/main")
@Component
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        try {
            model.addAttribute("role", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index.jsp";
    }
}
