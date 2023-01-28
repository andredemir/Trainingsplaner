package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public
class CustomerAlreadyExistingException extends Exception {


    private final Long ID;
    private final String email;


    public CustomerAlreadyExistingException(Long ID) {
        super(String.format("Customer with ID %d does already exist.", ID));

        this.ID = ID;
        this.email = null;
    }

    public CustomerAlreadyExistingException(String email) {
        super("Customer with email " + email + " does already exist.");
        this.email = email;
        this.ID = null;
    }
}