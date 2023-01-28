package com.haw.srs.customerservice.wrapperklassen;


import com.haw.srs.customerservice.entities.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A wrapper class used in CustomerFacade
 */
@Data
@NoArgsConstructor
public class UsernameUpdateCustomerWrapper {

    String username;
    Customer customer;
}
