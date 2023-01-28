package com.haw.srs.customerservice.controller;

import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.User;
import com.haw.srs.customerservice.facades.CustomerFacade;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.UserRepository;
import com.haw.srs.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class SecurityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    //https://www.baeldung.com/spring-security-method-security

    /**
     * A method for getting the username of the loged in customer
     */
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

    /**
     * A method for getting the currently logged in user as an object
     */
    @RequestMapping(value = "/userobject", method = RequestMethod.GET)
    @ResponseBody
    public User currentUserObject(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).get();
    }

    /**
     * A method for getting the currently logged in customer as an object
     */
    @RequestMapping(value = "/customerobject", method = RequestMethod.GET)
    @ResponseBody
    public Customer currentCustomerObject(Authentication authentication) {
        return customerRepository.findByEmail(authentication.getName()).get();
    }

}
