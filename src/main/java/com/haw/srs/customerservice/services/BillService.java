package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.exceptions.BillAlreadyExistingException;
import com.haw.srs.customerservice.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * A service class of methods for Bills
 */
@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Transactional
    public Long createBill(Date date, Address billAddress, Address deliveryAddress) throws BillAlreadyExistingException {
        Bill bill = new Bill(date, billAddress, deliveryAddress);
        if (billRepository.findByBillId(bill.getBillId()).isPresent()) {
            throw new BillAlreadyExistingException(bill.getBillId());
        }
        billRepository.save(bill);
        return bill.getBillId();
    }

    @Transactional
    public Bill createBillwithDiffAddresses(Date date, Address billAddress, Address deliveryAddress) throws BillAlreadyExistingException {
        Bill bill = new Bill(date, billAddress, deliveryAddress);
        if (billRepository.findByBillId(bill.getBillId()).isPresent()) {
            throw new BillAlreadyExistingException(bill.getBillId());
        }
        billRepository.save(bill);
        return bill;
    }

    @Transactional
    public Bill createBillwithSameAddresses(Date date, Address deliveryAddress) throws BillAlreadyExistingException {
        Bill bill = new Bill(date, deliveryAddress);
        if (billRepository.findByBillId(bill.getBillId()).isPresent()) {
            throw new BillAlreadyExistingException(bill.getBillId());
        }
        billRepository.save(bill);
        return bill;
    }

    public List<Bill> findAllBill() {
        return billRepository.findAll();
    }

}
