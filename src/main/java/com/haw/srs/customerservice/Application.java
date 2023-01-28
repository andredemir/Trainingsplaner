package com.haw.srs.customerservice;

import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.datatypes.PhoneNumber;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Role;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.repositories.RoleRepository;
import com.haw.srs.customerservice.services.CreditCardPayment;
import com.haw.srs.customerservice.services.CustomerService;
import com.haw.srs.customerservice.services.OrderService;
import com.haw.srs.customerservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}

@Component
class PopulateTestDataRunner implements CommandLineRunner {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BillRepository billRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public PopulateTestDataRunner(CustomerRepository customerRepository, BillRepository billRepository) {
        this.customerRepository = customerRepository;
        this.billRepository = billRepository;
        this.productRepository = productRepository;

        this.customerService = new CustomerService();
        this.orderService = new OrderService();
    }

    @Override
    public void run(String... args) throws CustomerAlreadyExistingException, ProductAlreadyInCartException, CustomerNotFoundException, ProductNotFoundException, EmptyCartException, OrderNotFoundException, BillAlreadyExistingException, ProductAlreadyExistingException {
        customerRepository.deleteAll();
        Arrays.asList(
                        "Miller,Doe,Smith".split(","))
                .forEach(
                        name -> customerRepository.save(new Customer(Gender.FEMALE, name + "@dummy.org", "sc3", "doe", "ddd", new Address("Haakestrasse", 23, "21056", "Hamburg")))
                );
        billRepository.save(new Bill(null, new Address("Haakestrasse", 23, "21056", "Hamburg"), new Address("Hallerstrasse", 266, "3423", "Hamburg")));
        Long customerID = customerService.createCustomer("Stefan", "Sarstedt", Gender.MALE, "stefan.sarstedt@haw-hamburg.de", null, new Address("Hallerstrasse",
                266, "3423", "Hamburg"), new CreditCardPayment(), new PhoneNumber("+49 176 787654"));
/*        Long product = productService.createProduct("MacBook Pro", "Ein Laptop mit einem geilem OS", 600.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "");
        Long product2 = productService.createProduct("Iphone14", "Ein Handy mit einem geilem OS", 1400.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "");
        Long product3 = productService.createProduct("Iphone 13 Pro", "Ein Handy mit einem geilem OS", 1300.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "");
        Long product4 = productService.createProduct("Ipad Pro", "Ein Handy mit einem geilem OS", 2200.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "");
        Long product5 = productService.createProduct("IMac Pro", "Ein Computer mit einem geilem OS", 1100.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "../img/png/no-image-icon-6.png");
        Long product6 = productService.createProduct("Apple TV", "Ein TV mit einem geilem OS", 1500.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, "../img/png/no-image-icon-6.png");
        Long product7 = productService.createProduct("Hund", "Ein Hund den ich nicht mehr brauche", 1500.00, Date.from(Instant.now()), ProductCategory.HAUSTIERE, "");
        customerService.addToCart(customerID, product);
        customerService.addToCart(customerID, product2);
        customerService.addToCart(customerID, product3);
        customerService.addToCart(customerID, product4);
        customerService.addToCart(customerID, product5);
        orderService.placeOrder(customerID, new Address("Haakestrasse", 23, "21056", "Hamburg"));*/
      //  Role admin = new Role();
      //  roleRepository.save(admin);
    }

}
