package com.haw.srs.customerservice.controller;

import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Role;
import com.haw.srs.customerservice.entities.User;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.payload.LoginDto;
import com.haw.srs.customerservice.payload.SignUpDto;
import com.haw.srs.customerservice.repositories.RoleRepository;
import com.haw.srs.customerservice.repositories.UserRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
//
//        // add check for username exists in a DB
//        if(userRepository.existsByUsername(signUpDto.getUsername())){
//            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if(userRepository.existsByEmail(signUpDto.getEmail())){
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // create user object
//        User user = new User();
////        user.setName(signUpDto.getName());
//        user.setUsername(signUpDto.getUsername());
//        user.setEmail(signUpDto.getEmail());
//        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
////WIP        user.setCustomer(new Customer());
//
//        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
//        user.setRoles(Collections.singleton(roles));
//
//
//        userRepository.save(user);
//
//        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
//
//    }

    /**
     * A method for authenticating a customer with spring security during login
     */
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateCustomer(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    /**
     * A method for registering a customer with spring security during sign up
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerCustomer(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(customerRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(customerRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        // create Customer object
        Customer customer = new Customer(signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getUsername());
        // create user object
   //     Role roles = roleRepository.findByName("ROLE_ADMIN").get();
   //     customer.setRoles(Collections.singleton(roles));

//WIP        user.setCustomer(new Customer());

    //    customer.setRoles(Collections.singleton(roles));

        customerRepository.save(customer);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

  //  @PostMapping("/logout")
  //  public ResponseEntity<String> authenticateUser(){
  //      authenticationManager.
  //  }
}
