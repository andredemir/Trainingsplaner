package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public
class ProductAlreadyExistingException extends Exception {

    private final Long ID;

    public ProductAlreadyExistingException(Long ID) {
        super(String.format("Product with ID %d does already exist.", ID));

        this.ID = ID;
    }
}