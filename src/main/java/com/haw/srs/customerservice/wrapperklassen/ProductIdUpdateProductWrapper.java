package com.haw.srs.customerservice.wrapperklassen;


import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A wrapper class used in CustomerFacade
 */
@Data
@NoArgsConstructor
public class ProductIdUpdateProductWrapper {

    Long productId;
    Product product;
}
