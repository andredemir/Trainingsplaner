package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.enums.Bodypart;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the Trainingsplan entity
 */

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "trainingplaene")
public class Trainingsplan {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long trainingsplanId;
        private String name;
        // Liste von Übungen
        @Getter
        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @Setter(AccessLevel.NONE)
        List<UebungForTrainingsplan> uebungen = new ArrayList<>();
        // Liste von Körperpartien


    public Trainingsplan(String name) {
        this.name = name;
    }

    public void addUebung(UebungForTrainingsplan uebung) {
        this.uebungen.add(uebung);
    }
}


