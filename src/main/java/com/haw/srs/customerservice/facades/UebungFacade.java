package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Uebung;
import com.haw.srs.customerservice.exceptions.BillNotFoundException;
import com.haw.srs.customerservice.exceptions.UebungNotFoundException;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.UebungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/uebungen")
public class UebungFacade {

    private final UebungRepository uebungRepository;

    @Autowired
    public UebungFacade(UebungRepository uebungRepository) {
        this.uebungRepository = uebungRepository;
    }


    /**
     * A method for getting all Uebung in the database
     */
    @GetMapping
    public List<Uebung> getUebungen() {
        return uebungRepository.findAll();
    }


    /**
     * A method for getting a specific Uebung corresponding to an id
     */
    @GetMapping(value = "/{id:[\\d]+}")
    public Uebung getUebung(@PathVariable("id") Long uebungId) throws UebungNotFoundException {
        return uebungRepository
                .findById(uebungId)
                .orElseThrow(() -> new UebungNotFoundException(uebungId));
    }

    /**
     * A method for deleting a specific Uebung corresponding to an id
     */
    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUebung(@PathVariable("id") Long uebungId) throws UebungNotFoundException {
        Uebung uebung = uebungRepository
                .findById(uebungId)
                .orElseThrow(() -> new UebungNotFoundException(uebungId));

        uebungRepository.delete(uebung);
    }

    /**
     * A method for creating a Uebung
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Uebung CreateUebung(@RequestBody Uebung uebung) {
        return uebungRepository.save(uebung);
    }

    /**
     * A method for updating a Uebung
     */
    @PutMapping
    public Uebung updateUebung(@RequestBody Uebung uebung) throws UebungNotFoundException {
        Uebung uebungToUpdate = uebungRepository
                .findById(uebung.getUebungId())
                .orElseThrow(() -> new UebungNotFoundException(uebung.getUebungId()));
        return uebungRepository.save(uebung);
    }
}
