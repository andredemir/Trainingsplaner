package com.haw.srs.customerservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
// https://www.javaguides.net/2019/10/spring-security-hibernate-database-authentication-example.html

@Controller
public class HomeController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {
            "/",
            "/welcome"
    }, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
}
