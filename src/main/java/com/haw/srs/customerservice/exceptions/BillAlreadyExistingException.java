package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public
class BillAlreadyExistingException extends Exception {

    private final Long ID;

    public BillAlreadyExistingException(Long ID) {
        super(String.format("Bill with ID %d does already exist.", ID));

        this.ID = ID;
    }
}