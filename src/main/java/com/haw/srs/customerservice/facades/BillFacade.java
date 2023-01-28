package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.exceptions.BillNotFoundException;
import com.haw.srs.customerservice.exceptions.CustomerNotFoundException;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/bills")
public class BillFacade {

    private final BillRepository billRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public BillFacade(BillRepository billRepository, CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
    }


    /**
     * A method for getting all bills in the database
     */
    @GetMapping
    public List<Bill> getBill() {
        return billRepository.findAll();
    }


    /**
     * A method for getting a specific bill corresponding to an id
     */
    @GetMapping(value = "/{id:[\\d]+}")
    public Bill getBill(@PathVariable("id") Long billId) throws BillNotFoundException {
        return billRepository
                .findById(billId)
                .orElseThrow(() -> new BillNotFoundException(billId));
    }

    /*
     *
    @GetMapping(value = "/billsfrom:{username}")
    public List<Bill> getBillByUsername(@PathVariable("username") String username) throws BillNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow((()-> new CustomerNotFoundException(username)));
        return customer.getBills();
    }
     */

    /**
     * A method for deleting a specific bill corresponding to an id
     */
    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBill(@PathVariable("id") Long billId) throws BillNotFoundException {
        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() -> new BillNotFoundException(billId));

        billRepository.delete(bill);
    }

    /**
     * A method for creating a bill
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bill createBill(@RequestBody Bill bill) {

        return billRepository.save(bill);
    }

    /**
     * A method for updating a bill
     */
    @PutMapping
    public Bill updateBill(@RequestBody Bill bill) throws BillNotFoundException {
        Bill billToUpdate = billRepository
                .findById(bill.getBillId())
                .orElseThrow(() -> new BillNotFoundException(bill.getBillId()));

        return billRepository.save(bill);
    }
}
