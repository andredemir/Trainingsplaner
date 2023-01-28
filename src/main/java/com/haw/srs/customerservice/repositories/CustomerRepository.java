package com.haw.srs.customerservice.repositories;

import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for all customers
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(Long customerId);
    Optional<Customer> findByFirstName(String firstName);
    Optional<Customer> findByLastName(String firstName);

    Optional<Customer> findByUsername(String username);



    Optional<Customer> findByUsernameOrEmail(String username, String email);
    Optional<Customer> findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
