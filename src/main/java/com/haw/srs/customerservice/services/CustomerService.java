package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.datatypes.PhoneNumber;
import com.haw.srs.customerservice.entities.*;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.interfaces.PaymentType;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.wrapperklassen.ProductUsernameWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class of methods for Customers
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    public Long createCustomer(String firstName, String lastName, Gender gender, String email, String password, Address address, PaymentType paymentType, PhoneNumber telefonummer) throws CustomerAlreadyExistingException {
        Customer customer = new Customer(gender, email, password, firstName, lastName, address, paymentType,telefonummer);
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new CustomerAlreadyExistingException(email);
        }
        customerRepository.save(customer);
        return customer.getCustomerId();
    }


    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }


    public void changePassword(String oldPassword, String newPassword, long customerId) throws WrongPasswordException {
        if (customerRepository.getById(customerId).getPassword().equals(oldPassword)){
            customerRepository.getById(customerId).setPassword(newPassword);
        }else throw new WrongPasswordException(customerId);
    }

    //@Transactional
    public void placeProduct(Long customerId, Long productId) throws CustomerNotFoundException, ProductNotFoundException, ProductAlreadyPlacedException {
        Customer customer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if (customer.getPlacedProducts().contains(product)) {
            throw new ProductAlreadyPlacedException(productId, customerId);
        }
        customer.addPlacedProduct(product);
        customerRepository.save(customer);

    }

    /**
     * Place product with a Wrapper object
     * @param productUsernameWrapper
     * @throws CustomerNotFoundException
     * @throws ProductAlreadyPlacedException
     */
    @Transactional
    public void placeProductWrapper(ProductUsernameWrapper productUsernameWrapper) throws CustomerNotFoundException, ProductAlreadyPlacedException {
        Product product = productUsernameWrapper.getProduct();

        Customer customer = customerRepository.findByUsername(productUsernameWrapper.getUsername())
                .orElseThrow(() -> new CustomerNotFoundException(productUsernameWrapper.getUsername()));
        if (customer.getPlacedProducts().stream().anyMatch(p -> p.getName().equals(product.getName()))) {
            throw new ProductAlreadyPlacedException(product.getProductId(), customer.getCustomerId());
        }
        productRepository.save(product);
        customer.addPlacedProduct(product);
        customerRepository.save(customer);

    }

    //@Transactional
    public void deleteProductFromPlacedProducts(Long customerID, Long productId) throws ProductNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() -> new CustomerNotFoundException(customerID));
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if(customer.getPlacedProducts().contains(product)){
            customer.getPlacedProducts().remove(product);
        }else {
            throw new ProductNotFoundException(productId);
        }
    }

    public List<Product> findProduct(String produktname){
        return productRepository.findAll().stream().filter(product -> produktname.matches(product.getName())).collect(Collectors.toList());

    }

    @Transactional
    public void addToCart(Long customerId, Long productId) throws CustomerNotFoundException, ProductNotFoundException, ProductAlreadyInCartException {
        Customer customer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if (customer.getProducts().contains(product)) {
            throw new ProductAlreadyInCartException(productId, customerId);
        }
        customer.addProduct(product);
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteProductFromCart(Long customerID, Long productId) throws CustomerNotFoundException, ProductNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerID).orElseThrow(() -> new CustomerNotFoundException(customerID));
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if(customer.getProducts().contains(product)){
            customer.getProducts().remove(product);
        }else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Transactional
    public void emptyCart(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.getProducts().clear();
        customerRepository.save(customer);

    }

    @Transactional
    public void addOrder(Long customerId, Long orderId) throws CustomerNotFoundException, OrderNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        customer.addOrder(order);
        customerRepository.save(customer);
    }

}
