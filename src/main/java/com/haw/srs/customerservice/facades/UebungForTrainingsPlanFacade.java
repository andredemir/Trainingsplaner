package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Uebung;
import com.haw.srs.customerservice.entities.UebungForTrainingsplan;
import com.haw.srs.customerservice.exceptions.UebungForTrainingsplanNotFoundException;
import com.haw.srs.customerservice.exceptions.UebungNotFoundException;
import com.haw.srs.customerservice.repositories.UebungForTrainingsplanRepository;
import com.haw.srs.customerservice.repositories.UebungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/uebungenForTrainingsplan")
public class UebungForTrainingsPlanFacade {

    private final UebungForTrainingsplanRepository uebungForTrainingsplanRepository;

    @Autowired
    public UebungForTrainingsPlanFacade(UebungForTrainingsplanRepository uebungForTrainingsplanRepository) {
        this.uebungForTrainingsplanRepository = uebungForTrainingsplanRepository;
    }


    /**
     * A method for getting all Uebung in the database
     */
    @GetMapping
    public List<UebungForTrainingsplan> getUebungenForTrainingsplan() {
        return uebungForTrainingsplanRepository.findAll();
    }


    /**
     * A method for getting a specific Uebung corresponding to an id
     */
    @GetMapping(value = "/{id:[\\d]+}")
    public UebungForTrainingsplan getUebungForTrainingsplan(@PathVariable("id") Long uebungId) throws UebungForTrainingsplanNotFoundException {
        return uebungForTrainingsplanRepository
                .findById(uebungId)
                .orElseThrow(() -> new UebungForTrainingsplanNotFoundException(uebungId));
    }

    /**
     * A method for deleting a specific Uebung corresponding to an id
     */
    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUebungForTrainingsplan(@PathVariable("id") Long uebungId) throws UebungForTrainingsplanNotFoundException, UebungNotFoundException {
        UebungForTrainingsplan uebung = uebungForTrainingsplanRepository
                .findById(uebungId)
                .orElseThrow(() -> new UebungNotFoundException(uebungId));

        uebungForTrainingsplanRepository.delete(uebung);
    }

    /**
     * A method for creating a Uebung
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UebungForTrainingsplan CreateUebung(@RequestBody UebungForTrainingsplan uebung) {
        return uebungForTrainingsplanRepository.save(uebung);
    }

    /**
     * A method for updating a Uebung
     */
    @PutMapping
    public UebungForTrainingsplan updateUebung(@RequestBody UebungForTrainingsplan uebung) throws UebungForTrainingsplanNotFoundException {
        UebungForTrainingsplan uebungToUpdate = uebungForTrainingsplanRepository
                .findById(uebung.getUebungForTrainingsPlanId())
                .orElseThrow(() -> new UebungForTrainingsplanNotFoundException(uebung.getUebungsId()));
        return uebungForTrainingsplanRepository.save(uebungToUpdate);
    }
}
