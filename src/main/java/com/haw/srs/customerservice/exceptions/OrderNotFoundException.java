package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public
class OrderNotFoundException extends Exception {

    private final Long orderId;

    public OrderNotFoundException(Long orderId) {
        super(String.format("Could not find order with number %d.", orderId));
        this.orderId = orderId;
    }
}