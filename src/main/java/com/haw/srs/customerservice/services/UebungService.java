package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Uebung;
import com.haw.srs.customerservice.enums.Bodypart;
import com.haw.srs.customerservice.enums.Category;
import com.haw.srs.customerservice.exceptions.BillAlreadyExistingException;
import com.haw.srs.customerservice.exceptions.UebungAlreadyExistingException;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.UebungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * A service class of methods for Bills
 */
@Service
public class UebungService {
    @Autowired
    private UebungRepository uebungRepository;

    @Transactional
    public Long createUebung(String name, Category category, Bodypart bodypart) throws  UebungAlreadyExistingException {
        Uebung uebung = new Uebung(name, bodypart, category);
        if (uebungRepository.findByUebungId(uebung.getUebungId()).isPresent()) {
            throw new UebungAlreadyExistingException(uebung.getUebungId());
        }
        uebungRepository.save(uebung);
        return uebung.getUebungId();
    }




    public List<Uebung> findAllBill() {
        return uebungRepository.findAll();
    }

}
