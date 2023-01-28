package com.haw.srs.customerservice.facadeTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.exceptions.CustomerNotFoundException;
import com.haw.srs.customerservice.exceptions.ProductNotFoundException;
import com.haw.srs.customerservice.facades.ProductFacade;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.wrapperklassen.ProductUsernameWrapper;
import com.haw.srs.customerservice.wrapperklassen.UsernameProductIDWrapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductFacadeTest {

    private final Log log = LogFactory.getLog(getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductFacade productFacade;

    private Product product;

    @BeforeEach
    void setUp() {
        //this.productRepository.deleteAll();

        product = this.productRepository.save(new Product("MacBook", "Baba Laptop", 999.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, null));

        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    void getAllProductsSuccess() {
        //@formatter:off
        given().
                // add this here to log request --> log().all().
        when().
                get("/products").
        then().
                // add this here to log response --> log().all().
                statusCode(HttpStatus.OK.value()).
                body("name", hasItems("MacBook"));
        //@formatter:on
    }

    @Test
    void getProductSuccess() {
        //@formatter:off
        given().
        when().
                get("/products/id/{id}", product.getProductId()).
        then().
                statusCode(HttpStatus.OK.value());
                //body("name", equalTo("MacBook"));
        //@formatter:on
    }

    @Test
    void getProductFailBecauseOfNotFound() {
        //@formatter:off
        given().
        when().
                get("/products/id/{id}", Integer.MAX_VALUE).
        then().
                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }

    @Test
    void createProductSuccess() {
        //@formatter:off
        Product newProduct = new Product("Iphone", "Geiles Handy", 1590.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Customer customer = new Customer(Gender.MALE, "andredemir35@gmail.com","sc3", "andre", "demir", null);
        customer.setUsername("test_username");
        customerRepository.save(customer);
        ProductUsernameWrapper body = new ProductUsernameWrapper();
        body.setProduct(newProduct);
        body.setUsername(customer.getUsername());

        given().
                contentType(ContentType.JSON).
                body(body).
        when().
                post("/products").
        then().
                statusCode(HttpStatus.CREATED.value());
                //body("productId", is(greaterThan(0)));
        //@formatter:on
    }

/*    @Test
    void updateCustomerSuccess() {
        customer.setCustomerName("Stefanie");

        //@formatter:off
        given().
                contentType(ContentType.JSON).
                body(ferry).
        when().
                put("/ferrys").
        then().
                statusCode(HttpStatus.OK.value());

        given().
        when().
                get("/ferrys/{id}", ferry.getFerryId()).
        then().
                statusCode(HttpStatus.OK.value()).
                body("ferryName", is(equalTo("Stefanie")));
        //@formatter:on
    }*/

    @Test
    void deleteProductSuccess() throws CustomerNotFoundException, ProductNotFoundException {
        //@formatter:off
        Product product = new Product("Iphone12", "Geiles Handy", 1590.00, Date.from(Instant.now()), ProductCategory.ELEKTRONIK, null);
        Customer customer = new Customer(Gender.MALE, "dom12@gmail.com","234", "dom", "torreto", null);
        customer.setUsername("test_username1");
        customerRepository.save(customer);
        productRepository.save(product);
        customer.addPlacedProduct(product);
        UsernameProductIDWrapper body = new UsernameProductIDWrapper();
        body.setProductid(product.getProductId());
        body.setUsername(customer.getUsername());
        given().
                contentType(ContentType.JSON).
                body(body).
        when().
                delete("/products/delete").
        then().
                statusCode(HttpStatus.OK.value());

//        given().
//        when().
//                get("/products/id/{id}", null).
//        then().
//                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }
}