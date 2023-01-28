package com.haw.srs.customerservice.serviceTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.*;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.services.CustomerService;
import com.haw.srs.customerservice.services.OrderService;
import com.haw.srs.customerservice.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderServiceTest {

    @Autowired OrderService orderService;

    @Autowired OrderRepository orderRepository;

    @Autowired CustomerService customerService;

    @Autowired CustomerRepository customerRepository;

    @Autowired ProductService productService;

    @Autowired BillRepository billRepository;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        orderRepository.deleteAll();
        billRepository.deleteAll();
    }

    @Test
    void createOrder() throws CustomerAlreadyExistingException, OrderAlreadyExistingException, CustomerNotFoundException {
        Long customerID = customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        Long orderId = orderService.createOrder(Time.from(Instant.now()), null, StatusType.OFFEN, new ArrayList<>(customer.getProducts()));

        Assertions.assertEquals(1, orderRepository.findAll().size());
        Assertions.assertTrue(orderRepository.existsById(orderId));
    }

    @Test
    void placeOrderWithBillAddressTest() throws CustomerAlreadyExistingException, CustomerNotFoundException, ProductAlreadyExistingException, ProductNotFoundException, ProductAlreadyInCartException, EmptyCartException, OrderNotFoundException, BillAlreadyExistingException, BillNotFoundException {
        Address deliveryAddress = new Address("Hallerstrasse",
                266, "3423", "Hamburg");
        Long customerID = customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", deliveryAddress, null, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(()
                ->new CustomerNotFoundException(customerID));
        Long productId = productService.createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Assertions.assertThrows(EmptyCartException.class, ()
                -> orderService.placeOrder(customerID,null));
        customerService.addToCart(customerID,productId);
        Address billAddress = new Address("Rechnungstrasse",
                34, "21534", "Hamburg");
        Order order = orderService.placeOrder(customerID, billAddress);
        Assertions.assertEquals(1, customer.getOrders().size());
        Assertions.assertTrue(customer.getProducts().isEmpty());
        Assertions.assertEquals(1, orderRepository.findAll().size());
        Bill bill = billRepository.findByBillId(order.getBillId()).orElseThrow(() -> new BillNotFoundException(order.getBillId()));
        Assertions.assertEquals(deliveryAddress, bill.getDeliveryAddress());
        Assertions.assertEquals(billAddress, bill.getBillAddress());
    }

    @Test
    void placeOrderNoBillAddressTest() throws CustomerAlreadyExistingException, ProductAlreadyExistingException, CustomerNotFoundException, ProductAlreadyInCartException, ProductNotFoundException, EmptyCartException, OrderNotFoundException, BillAlreadyExistingException, BillNotFoundException {
        Address address = new Address("Hallerstrasse",
                266, "3423", "Hamburg");
        Long customerID = customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", address, null, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(()
                ->new CustomerNotFoundException(customerID));
        Long productId = productService.createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Assertions.assertThrows(EmptyCartException.class, ()
                -> orderService.placeOrder(customerID,null));
        customerService.addToCart(customerID,productId);
        Order order = orderService.placeOrder(customerID, address);
        Assertions.assertEquals(1, customer.getOrders().size());
        Assertions.assertTrue(customer.getProducts().isEmpty());
        Assertions.assertEquals(1, orderRepository.findAll().size());
        Bill bill = billRepository.findByBillId(order.getBillId()).orElseThrow(() -> new BillNotFoundException(order.getBillId()));
        Assertions.assertEquals(address, bill.getBillAddress());
        Assertions.assertEquals(address, bill.getDeliveryAddress());
    }
}