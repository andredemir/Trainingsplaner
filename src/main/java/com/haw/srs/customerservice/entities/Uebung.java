package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.enums.Bodypart;
import com.haw.srs.customerservice.enums.Category;
import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.datatypes.Address;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for the Order entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "uebungen")
public class Uebung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Bodypart bodypart;
    private Category category;

    public Uebung(String name, String description, Bodypart bodypart, Category category) {
        this.name = name;
        this.description = description;
        this.bodypart = bodypart;
        this.category = category;
    }


}
