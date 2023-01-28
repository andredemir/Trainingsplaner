package com.haw.srs.customerservice.repositoryTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        this.customerRepository.deleteAll();

        Customer customer = new Customer(Gender.MALE, "andredemir35@gmail.com","sc3", "andre", "demir", null);
        customerRepository.save(customer);
    }

    @Test
    void findCustomerByLastNameSuccess() {
        assertThat(customerRepository.findByLastName("demir").isPresent()).isTrue();
    }

    @Test
    void findCustomerByLastNameFail() {
        assertThat(customerRepository.findByLastName("notExisting").isPresent()).isFalse();
    }
}