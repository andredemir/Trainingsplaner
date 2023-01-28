package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public
class WrongPasswordException extends Exception {

    private final Long ID;

    public WrongPasswordException(Long ID) {
        super(String.format("Wrong password for the customer with ID %d", ID));

        this.ID = ID;
    }
}