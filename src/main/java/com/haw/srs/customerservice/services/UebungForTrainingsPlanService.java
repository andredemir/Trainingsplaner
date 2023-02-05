package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.entities.Uebung;
import com.haw.srs.customerservice.entities.UebungForTrainingsplan;
import com.haw.srs.customerservice.enums.Bodypart;
import com.haw.srs.customerservice.enums.Category;
import com.haw.srs.customerservice.exceptions.UebungAlreadyExistingException;
import com.haw.srs.customerservice.repositories.UebungForTrainingsplanRepository;
import com.haw.srs.customerservice.repositories.UebungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A service class of methods for Bills
 */
@Service
public class UebungForTrainingsPlanService {
    @Autowired
    private UebungForTrainingsplanRepository uebungForTrainingsplanRepository;
    @Autowired
    UebungRepository uebungRepository;

    @Transactional
    public Long createUebungForTrainingsPlan(long uebungId, int wdh, int sets, double pause, int gewicht) throws  UebungAlreadyExistingException {
        Uebung uebung = uebungRepository.findByUebungId(uebungId).orElseThrow(() -> new UebungAlreadyExistingException(uebungId));
        UebungForTrainingsplan uebungForTrainingsplan = new UebungForTrainingsplan(uebung.getName(), uebungId, wdh, sets, pause, gewicht);
        if (uebungForTrainingsplanRepository.findByUebungForTrainingsPlanId(uebungForTrainingsplan.getUebungForTrainingsPlanId()).isPresent()) {
            throw new UebungAlreadyExistingException(uebungId);
        }
        uebungForTrainingsplanRepository.save(uebungForTrainingsplan);
        return uebungForTrainingsplan.getUebungForTrainingsPlanId();
    }

    public UebungForTrainingsplan findAllUebungenForTrainingsplaeneWithId(long trainingsplanId) {
        return uebungForTrainingsplanRepository.getById(trainingsplanId);
    }

}
