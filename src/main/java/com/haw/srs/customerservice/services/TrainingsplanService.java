package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.entities.*;
import com.haw.srs.customerservice.enums.Bodypart;
import com.haw.srs.customerservice.enums.Category;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.TrainingsplanRepository;
import com.haw.srs.customerservice.repositories.UebungForTrainingsplanRepository;
import com.haw.srs.customerservice.repositories.UebungRepository;
import com.haw.srs.customerservice.wrapperklassen.ProductUsernameWrapper;
import com.haw.srs.customerservice.wrapperklassen.TrainingsplanUebungWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A service class of methods for Bills
 */
@Service
public class TrainingsplanService {
    @Autowired
    private TrainingsplanRepository trainingsplanRepository;
    @Autowired
    private UebungRepository uebungRepository;

    @Autowired
    UebungForTrainingsplanRepository uebungForTrainingsplanRepository;

    @Transactional
    public Long createTrainingsplan(String name) throws TrainingsplanAlreadyExistingException {
        Trainingsplan trainingsplan = new Trainingsplan(name);
        if (trainingsplanRepository.findByTrainingsplanId(trainingsplan.getTrainingsplanId()).isPresent()) {
            throw new TrainingsplanAlreadyExistingException(trainingsplan.getTrainingsplanId());
        }
        trainingsplanRepository.save(trainingsplan);
        return trainingsplan.getTrainingsplanId();
    }


    @Transactional
    public void addToTrainingsplan(Long trainingsplanId, Long uebungId) throws UebungAlreadyExistingException, UebungNotFoundException, TrainingsplanNotFoundException, UebungForTrainingsplanAlreadyExistingException {
        Trainingsplan trainingsplan = trainingsplanRepository.findByTrainingsplanId(trainingsplanId).orElseThrow(() -> new TrainingsplanNotFoundException(trainingsplanId));
        UebungForTrainingsplan uebungForTrainingsplan = uebungForTrainingsplanRepository.findByUebungForTrainingsPlanId(uebungId).orElseThrow(() -> new UebungNotFoundException(uebungId));
        if (trainingsplan.getUebungen().contains(uebungForTrainingsplan)) {
            throw new UebungForTrainingsplanAlreadyExistingException(uebungForTrainingsplan.getUebungForTrainingsPlanId());
        }
        trainingsplan.addUebung(uebungForTrainingsplan);
        trainingsplanRepository.save(trainingsplan);
    }


    public List<Trainingsplan> findAllTrainingsplaene() {
        return trainingsplanRepository.findAll();
    }
}
