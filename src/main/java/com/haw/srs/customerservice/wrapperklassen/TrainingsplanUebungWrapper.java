package com.haw.srs.customerservice.wrapperklassen;

import com.haw.srs.customerservice.entities.Trainingsplan;
import com.haw.srs.customerservice.entities.Uebung;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A wrapper class used in a ProductFacade test
 */
@Data
@NoArgsConstructor
public class TrainingsplanUebungWrapper {
    Uebung uebung;
    Trainingsplan trainingsplan;

    public TrainingsplanUebungWrapper(Uebung uebung, Trainingsplan trainingsplan) {
        this.uebung = uebung;
        this.trainingsplan = trainingsplan;
    }
}
