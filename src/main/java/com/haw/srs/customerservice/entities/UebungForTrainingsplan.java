package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.enums.Bodypart;
import com.haw.srs.customerservice.enums.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * A class for the Order entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "uebungenForTrainingsplaene")
public class UebungForTrainingsplan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uebungForTrainingsPlanId;
    private long uebungsId;
    private int wdh;
    private int sets;
    private double pause;
    private int gewicht;

    public UebungForTrainingsplan(long uebungId, int wdh, int sets, double pause, int gewicht) {
        this.uebungsId = uebungId;
        this.wdh = wdh;
        this.sets = sets;
        this.pause = pause;
        this.gewicht = gewicht;
    }
}
