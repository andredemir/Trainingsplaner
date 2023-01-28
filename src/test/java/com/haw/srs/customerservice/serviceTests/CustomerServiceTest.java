package com.haw.srs.customerservice.serviceTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.entities.*;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.services.CustomerService;
import com.haw.srs.customerservice.services.MailGateway;
import com.haw.srs.customerservice.services.OrderService;
import com.haw.srs.customerservice.services.ProductService;
import com.haw.srs.customerservice.wrapperklassen.ProductUsernameWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CustomerServiceTest {
    @Autowired
    CustomerRepository customerRepository;

    @MockBean
    private MailGateway mailGateway;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }


    @Test
    void createCustomerTest() throws CustomerAlreadyExistingException {
        customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Assertions.assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void changePasswordTest() throws WrongPasswordException, CustomerAlreadyExistingException, CustomerNotFoundException {
        Long customerID = customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        customerService.changePassword(customer.getPassword(), "sc4", customerID);
        Assertions.assertEquals("sc4", customer.getPassword());
    }

    @Test
    void addToCartTest() throws CustomerAlreadyExistingException, ProductAlreadyExistingException, ProductAlreadyInCartException, CustomerNotFoundException, ProductNotFoundException {
        Long customerID = customerService.createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Long productId = productService.createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService.createProduct("Oculus quest 2", "VR!", 200.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));

        customerService.addToCart(customerID,productId);
        customerService.addToCart(customerID,product2Id);
        Assertions.assertEquals(customer.getProducts().size(), 2);
    }

    @Test
    void deleteProductFromCartTest() throws CustomerAlreadyExistingException, ProductAlreadyExistingException, CustomerNotFoundException, ProductNotFoundException, ProductAlreadyInCartException {
        Long productId = productService
                .createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Long customerID = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Product product = productRepository
                .findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Customer customer = customerRepository
                .findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        customerService.addToCart(customerID, productId);
        customerService.deleteProductFromCart(customer.getCustomerId(), product.getProductId());
        Assertions.assertEquals(0, customer.getProducts().size());
    }

    @Test
    void placeProductTest() throws ProductAlreadyExistingException, CustomerAlreadyExistingException, CustomerNotFoundException, ProductAlreadyPlacedException, ProductNotFoundException {
        Long customerID = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Long productId = productService
                .createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService
                .createProduct("Oculus quest 2", "VR!", 200.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Customer customer = customerRepository
                .findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        Product product = productRepository
                .findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Product product2 = productRepository
                .findByProductId(product2Id).orElseThrow(() -> new ProductNotFoundException(productId));


        customerService.placeProduct(customerID, productId);
        customerService.placeProduct(customerID, product2Id);
        Assertions.assertTrue(customer.getPlacedProducts().contains(product));
        Assertions.assertTrue(customer.getPlacedProducts().contains(product2));
        Assertions.assertEquals(2, customer.getPlacedProducts().size());
    }

    @Test
    void placeProductWithWrapperTest() throws CustomerAlreadyExistingException, CustomerNotFoundException, ProductAlreadyPlacedException {
        ProductUsernameWrapper productUsernameWrapper = new ProductUsernameWrapper();
        productUsernameWrapper.setProduct(
                new Product("Oculus quest 2", "VR!", 200.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null));
        Long customerID = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        productUsernameWrapper.setUsername(customer.getUsername());

        customerService.placeProductWrapper(productUsernameWrapper);
        Assertions.assertTrue(customer.getPlacedProducts().contains(productUsernameWrapper.getProduct()));
        Assertions.assertEquals(1, customer.getPlacedProducts().size());
    }

    @Test
    void deleteProductFromPlacedProductsTest() throws CustomerAlreadyExistingException, ProductAlreadyExistingException, CustomerNotFoundException, ProductNotFoundException, ProductAlreadyPlacedException {
        Long productId = productService
                .createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Long customerID = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Product product = productRepository
                .findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Customer customer = customerRepository
                .findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));
        customerService.placeProduct(customerID, productId);
        customerService.deleteProductFromPlacedProducts(customer.getCustomerId(), product.getProductId());
        Assertions.assertEquals(0, customer.getPlacedProducts().size());

    }

    @Test
    void findProductTest() throws ProductAlreadyExistingException {
        productService.createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Assertions.assertEquals(1, customerService.findProduct("SONY PLAYSTATION 5").size());
    }

    @Test
    void emptyCartTest() throws CustomerAlreadyExistingException, ProductAlreadyExistingException, ProductNotFoundException, CustomerNotFoundException, ProductAlreadyInCartException {
        Long customerID = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Long productId = productService
                .createProduct("SONY PLAYSTATION 5", "GEILES STÜCK", 10.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService
                .createProduct("Oculus quest 2", "VR!", 200.00, Time.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Customer customer = customerRepository
                .findByCustomerId(customerID).orElseThrow(() ->new CustomerNotFoundException(customerID));

        customerService.addToCart(customerID,productId);
        customerService.addToCart(customerID,product2Id);
        customerService.emptyCart(customerID);
        Assertions.assertEquals(0, customer.getProducts().size());
    }

    @Test
    void addOrderTest() throws CustomerAlreadyExistingException, CustomerNotFoundException, OrderAlreadyExistingException, OrderNotFoundException {
        Long customerId = customerService
                .createCustomer("Jane", "Doe", Gender.FEMALE, "jane.doe@gmail.com", "sc3", null, null, null);
        Customer customer = customerRepository
                .findByCustomerId(customerId).orElseThrow(() ->new CustomerNotFoundException(customerId));
        Long orderId = orderService
                .createOrder(Time.from(Instant.now()), null, StatusType.OFFEN, new ArrayList<>(customer.getProducts()));
        Order order = orderRepository
                .findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

        customerService.addOrder(customerId, orderId);
        Assertions.assertTrue(customer.getOrders().contains(order));
        Assertions.assertEquals(1, customer.getOrders().size());

    }
}
