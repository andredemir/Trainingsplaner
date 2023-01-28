package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.datatypes.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for the Order entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Date date;
    private Address deliveryAddress;
    private Long billId;


    @Enumerated
    private StatusType statusType;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    //@Column(name = "products")
    //@JoinColumn(name = "order_Id", referencedColumnName = "orderId")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private List<Product> products = new LinkedList<>();

    public Order(Date date, Address deliveryAddress, StatusType statusType, List<Product> products, Long billId) {
        this.date = date;
        this.deliveryAddress = deliveryAddress;
        this.statusType = statusType;
        this.products = products;
        this.billId = billId;
    }

    public Order(Date date, Address deliveryAddress, StatusType statusType, List<Product> products) {
        this.date = date;
        this.deliveryAddress = deliveryAddress;
        this.statusType = statusType;
        this.products = products;
    }

    public Order(List<Product> produkte) {
        this.products = produkte;
    }
/*
    @JsonIgnore
    @Transient
    private Map<Line, List<Departure>> time;
*/


}
