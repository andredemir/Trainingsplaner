package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Trainingsplan;
import com.haw.srs.customerservice.entities.UebungForTrainingsplan;
import com.haw.srs.customerservice.exceptions.TrainingsplanNotFoundException;
import com.haw.srs.customerservice.repositories.TrainingsplanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/trainingsplaene")
public class TrainingsplanFacade {

    private final TrainingsplanRepository trainingsplanRepository;

    @Autowired
    public TrainingsplanFacade(TrainingsplanRepository trainingsplanRepository) {
        this.trainingsplanRepository = trainingsplanRepository;
    }


    /**
     * A method for getting all Trainingsplaene
     */
    @GetMapping
    public List<Trainingsplan> getTrainingsplaene() {
        return trainingsplanRepository.findAll();
    }


    /**
     * A method for getting a specific Uebung corresponding to an id
     */
    @GetMapping(value = "/{id:[\\d]+}")
    public Trainingsplan getTrainingplan(@PathVariable("id") Long trainingsplanId) throws TrainingsplanNotFoundException {
        return trainingsplanRepository
                .findById(trainingsplanId)
                .orElseThrow(() -> new TrainingsplanNotFoundException(trainingsplanId));
    }

    /**
     * A method for deleting a Trainingsplan
     */
    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainingsplan(@PathVariable("id") Long trainingsplanId) throws TrainingsplanNotFoundException {
        Trainingsplan trainingsplan = trainingsplanRepository
                .findById(trainingsplanId)
                .orElseThrow(() -> new TrainingsplanNotFoundException(trainingsplanId));

        trainingsplanRepository.delete(trainingsplan);
    }

    /**
     * A method for creating a Trainingsplan
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainingsplan createTrainingsplan(@RequestBody Trainingsplan trainingsplan) {
        return trainingsplanRepository.save(trainingsplan);
    }

    /**
     * A method for updating a Trainingsplan
     */
    @PutMapping
    public Trainingsplan updateTrainingsplan(@RequestBody Trainingsplan trainingsplan) throws TrainingsplanNotFoundException {
        Trainingsplan trainingsplanToUpdate = trainingsplanRepository
                .findById(trainingsplan.getTrainingsplanId())
                .orElseThrow(() -> new TrainingsplanNotFoundException(trainingsplan.getTrainingsplanId()));
        return trainingsplanRepository.save(trainingsplanToUpdate);
    }


}
