package com.haw.srs.customerservice.wrapperklassen;

import com.haw.srs.customerservice.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A wrapper class used in a ProductFacade test
 */
@Data
@NoArgsConstructor
public class UsernameProductIDWrapper {
    String username;
    long productid;

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
