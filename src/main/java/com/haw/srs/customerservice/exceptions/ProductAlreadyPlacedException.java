package com.haw.srs.customerservice.exceptions;

import com.haw.srs.customerservice.entities.Product;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


    @Value
    @EqualsAndHashCode(callSuper = false)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class ProductAlreadyPlacedException extends Exception {

        private final Long productID;
        private final Long customerID;
        private final Product product;


        public ProductAlreadyPlacedException(Long productID, Long customerID) {
            super(String.format("Product with ID %d is already in cart of customer with ID %d.", productID, customerID));

            this.productID = productID;
            this.customerID = customerID;
            this.product=null;
        }

        public ProductAlreadyPlacedException(Product product, Long customerID) {
            super(String.format("Product with name %s is already in cart of customer with ID %d.", product.getName(), customerID));

            this.productID = null;
            this.customerID = customerID;
            this.product = product;
        }
    }

