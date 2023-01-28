package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.datatypes.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * A class for the Bill entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long billId;
    private Date date;

    @Lob
    @Column
    private String billContent;

    private Double totalPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",column=@Column(name="Delivery_Street")),
            @AttributeOverride(name="houseNumber",column=@Column(name="Delivery_House_Number")),
            @AttributeOverride(name="postalCode",column=@Column(name="Delivery_Postal_Code")),
            @AttributeOverride(name="city",column=@Column(name="Delivery_City"))})
    private Address deliveryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",column=@Column(name="Bill_Street")),
            @AttributeOverride(name="houseNumber",column=@Column(name="Bill_House_Number")),
            @AttributeOverride(name="postalCode",column=@Column(name="Bill_Postal_Code")),
            @AttributeOverride(name="city",column=@Column(name="Bill_City"))})
    private Address billAddress;

    public Bill(Date date, Address billAddress, Address deliveryAddress) {
        this.date = date;
        this.deliveryAddress = deliveryAddress;
        this.billAddress = billAddress;
    }

    public Bill(int i){

    }

    public Bill(Date date, Address deliveryAddress) {
        this.date = date;
        this.deliveryAddress = deliveryAddress;
        this.billAddress = deliveryAddress;
    }

}
