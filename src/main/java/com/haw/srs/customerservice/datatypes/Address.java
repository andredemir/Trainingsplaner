package com.haw.srs.customerservice.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// this represents an immutable datatype (no setter)
// we cannot use @Value from lombok since jpa needs a noargconstructor when deserializing
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private Integer houseNumber;
    private String postalCode;
    private String city;
}
