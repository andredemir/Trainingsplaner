package com.haw.srs.customerservice.serviceTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.exceptions.BillAlreadyExistingException;
import com.haw.srs.customerservice.exceptions.BillNotFoundException;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.services.BillService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BillServiceTest {

    @Autowired
    BillRepository billRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BillService billService;

    Address deliveryAddress = new Address("Hallerstrasse",
            266, "3423", "Hamburg");
    Address billAddress = new Address("Rechnungstrasse",
            34, "21534", "Hamburg");
    Date date = Time.from(Instant.now());


    @BeforeEach
    void setup() {
        billRepository.deleteAll();
        customerRepository.deleteAll();
    }


    @Test
    void createBillwithDiffAddresses() throws BillAlreadyExistingException, BillNotFoundException {
        Long billId = billService.createBill(date, billAddress, deliveryAddress);
        Bill bill = billRepository.findByBillId(billId).orElseThrow(() -> new BillNotFoundException(billId));

        Assertions.assertTrue(billRepository.existsById(billId));
        Assertions.assertNotEquals(bill.getBillAddress(), bill.getDeliveryAddress());
    }

    @Test
    void createBillwithSameAddresses() throws BillAlreadyExistingException, BillNotFoundException {
        Long billId = billService.createBill(date, deliveryAddress, deliveryAddress);
        Bill bill = billRepository.findByBillId(billId).orElseThrow(() -> new BillNotFoundException(billId));

        Assertions.assertTrue(billRepository.existsById(billId));
        Assertions.assertEquals(bill.getBillAddress(), bill.getDeliveryAddress());

    }
}