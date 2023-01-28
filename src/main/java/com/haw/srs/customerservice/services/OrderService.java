package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A service class of methods for Orders
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BillService billService;
    @Autowired
    private CustomerService customerService;


    @Transactional
    public Long createOrder(Date date, Address lieferadresse, StatusType statusType, List<Product> products) throws OrderAlreadyExistingException {
        Order order = new Order(date, lieferadresse, statusType, products);
        if (orderRepository.findByOrderId(order.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistingException(order.getOrderId());
        }
        orderRepository.save(order);
        return order.getOrderId();
    }

    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order placeOrder(Long customerId, @Nullable Address billAddress) throws CustomerNotFoundException, BillAlreadyExistingException, EmptyCartException, OrderNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        if(customer.getProducts().isEmpty()){
            throw new EmptyCartException(customerId);
        }
        Date date = Time.from(Instant.now());
        Bill bill;
        if(billAddress == null){
            bill = billService.createBillwithSameAddresses(date, customer.getAddress());
        }else {
            bill = billService.createBillwithDiffAddresses(date, billAddress, customer.getAddress());
        }
        Order newOrder = new Order(date, customer.getAddress(), StatusType.VORBEREITUNG, new ArrayList<>(customer.getProducts()), bill.getBillId());
        newOrder = orderRepository.save(newOrder);
        customerService.emptyCart(customerId);
        customerService.addOrder(customerId, newOrder.getOrderId());
        return newOrder;
    }

}
