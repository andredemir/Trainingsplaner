package com.haw.srs.customerservice.wrapperklassen;

import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * A wrapper class used in ProductFacade
 */
@Data
@NoArgsConstructor
public class ProductUsernameWrapper {
    String username;
    Product product;

  //  @Id
  //  @GeneratedValue(strategy = GenerationType.AUTO)
  //  private Long productId;
  //  private String name;
  //  private String beschreibung;
  //  private Double preis;
  //  private Date erstellungsdatum;
  //  private ProductCategory productCategory;
  //  @Nullable
  //  private String productImage;

}
