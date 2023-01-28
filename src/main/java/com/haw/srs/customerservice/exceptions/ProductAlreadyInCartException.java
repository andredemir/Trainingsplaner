package com.haw.srs.customerservice.exceptions;


import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductAlreadyInCartException extends Exception {

    private final Long productID;
    private final Long customerID;

    public ProductAlreadyInCartException(Long productID, Long customerID) {
        super(String.format("Product with ID %d is already in cart of customer with ID %d.", productID, customerID));

        this.productID = productID;
        this.customerID = customerID;
    }
}
