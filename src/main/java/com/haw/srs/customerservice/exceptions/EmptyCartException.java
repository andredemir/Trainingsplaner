package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyCartException extends Exception{

    private final Long customerId;

    public EmptyCartException(Long customerId) {
        super(String.format("Could not find products in cartof customer with ID: %d.", customerId));
        this.customerId = customerId;
    }
}
