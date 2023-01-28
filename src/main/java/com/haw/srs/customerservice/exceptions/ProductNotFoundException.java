package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends Exception {

    private final Long productId;
    private final String productName;


    public ProductNotFoundException(Long productId) {
        super(String.format("Could not find product with number %d.", productId));
        this.productId = productId;
        this.productName = null;
    }

    public ProductNotFoundException(String productName) {
        super(String.format("Could not find product with name %d.", productName));
        this.productId = null;
        this.productName = productName;
    }
}
