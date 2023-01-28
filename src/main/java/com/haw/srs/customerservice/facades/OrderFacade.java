package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.exceptions.OrderNotFoundException;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/orders")
public class OrderFacade {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderFacade(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getOrder() {
        return orderRepository.findAll();
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public Order getOrder(@PathVariable("id") Long orderId) throws OrderNotFoundException {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable("id") Long orderId) throws OrderNotFoundException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        orderRepository.delete(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {

        return orderRepository.save(order);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) throws OrderNotFoundException {
        Order orderToUpdate = orderRepository
                .findById(order.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(order.getOrderId()));

        return orderRepository.save(order);
    }
}
