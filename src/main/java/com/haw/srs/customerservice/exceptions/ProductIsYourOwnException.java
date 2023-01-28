package com.haw.srs.customerservice.exceptions;


import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductIsYourOwnException extends Exception {

    private final Long productID;
    private final Long customerID;

    public ProductIsYourOwnException(Long productID, Long customerID) {
        super(String.format("Product with ID %d is your own, you cant buy it!", productID, customerID));

        this.productID = productID;
        this.customerID = customerID;
    }
}
