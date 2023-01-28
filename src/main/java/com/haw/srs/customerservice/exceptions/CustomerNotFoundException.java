package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public
class CustomerNotFoundException extends Exception {

    private final Long customerId;
    private final String lastName;

    public CustomerNotFoundException(Long customerId) {
        super(String.format("Could not find customer with ID: %d.", customerId));

        this.customerId = customerId;
        this.lastName = "";
    }

    //  public CustomerNotFoundException(String username) {
    //      super(String.format("Could not find customer with ID: %d.", customerId));
//
    //      this.customerId = Long.valueOf(0);
    //      this.lastName = username;
    //  }

    public CustomerNotFoundException(String lastName) {
        super(String.format("Could not find customer with lastname %s.", lastName));

        this.customerId = 0L;
        this.lastName = lastName;
    }
}