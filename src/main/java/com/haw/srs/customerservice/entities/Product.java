package com.haw.srs.customerservice.entities;

import com.haw.srs.customerservice.enums.ProductCategory;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

/**
 * A class for the Product entity
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String name;
    private String beschreibung;
    private Double preis;
    private Date erstellungsdatum;
    private ProductCategory productCategory;

    private Long ownerId;

    private boolean isInCart = false;
    @Lob
    @Column
    @Nullable
    private String productImage;

    public Product(String name, String beschreibung, Double preis, Date erstellungsdatum, ProductCategory productCategory, String productImage) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.erstellungsdatum = erstellungsdatum;
        this.productCategory = productCategory;
        this.productImage = productImage;

    }

    public String toString(){
        String string = ("\n ID: \n      "+ this.productId + "\n Name: \n      "+ this.name + "\n Kategorie: \n      " + productCategory + "\n Preis: \n      " + preis + "\n Verkaeufer: \n      " + ownerId);

        return string;
    }
}
