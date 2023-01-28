package com.haw.srs.customerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haw.srs.customerservice.datatypes.PhoneNumber;
import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.interfaces.PaymentType;
import com.haw.srs.customerservice.datatypes.Address;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A class for the Customer entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    private String username;
    private String email;

    private String password;
    @Enumerated
    private Gender gender;

    private String firstName;
    private String lastName;
    private Address address;
    private PaymentType paymentType;
    private PhoneNumber phoneNumber;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    // Bestellungen die zum Kunde gehören
    private List<Bill> bills = new LinkedList<>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    // Produkte die vom Kunde eingestellt sind
    private List<Product> placedProducts = new ArrayList<>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    // Bestellungen die zum Kunde gehören
    private List<Order> orders = new LinkedList<>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    // Produkte die gekauft werden können
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_", referencedColumnName = "customerId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;


    public Customer(Gender gender, String email, String password, String firstName, String lastName, Address address, PaymentType paymentType, PhoneNumber phoneNumber) {
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentType = paymentType;
        this.phoneNumber = phoneNumber;
    }

    public Customer(Gender gender, String email, String password, String firstName, String lastName, Address address) {
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentType = null;
        this.phoneNumber = null;
    }

    public Customer(String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.paymentType = null;
        this.phoneNumber = null;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addPlacedProduct(Product product) {
        this.placedProducts.add(product);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void addBill(Bill bill) {
        this.bills.add(bill);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void removePlacedProduct(Product product) {
        this.placedProducts.remove(product);
    }



}
