package com.haw.srs.customerservice.facadeTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.enums.Gender;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
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
class BillFacadeTest {

    private final Log log = LogFactory.getLog(getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private BillRepository billRepository;

    private Bill bill;

    @BeforeEach
    void setUp() {
        this.billRepository.deleteAll();

        bill = this.billRepository.save(new Bill(Date.from(Instant.now()), new Address("haakestraße", 55, "21075", "hamburg"), new Address("haakestraße", 40, "21075", "hamburg")));

        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    void getAllBillsSuccess() {
        //@formatter:off
        given().
                // add this here to log request --> log().all().
        when().
                get("/bills").
        then().
                // add this here to log response --> log().all().
                statusCode(HttpStatus.OK.value());
                //body("firstName", hasItems("andre"));
        //@formatter:on
    }

    @Test
    void getBillsSuccess() {
        //@formatter:off
        given().
        when().
                get("/bills/{id}", bill.getBillId()).
        then().
                statusCode(HttpStatus.OK.value());
                //body("firstName", equalTo("andre"));
        //@formatter:on
    }

    @Test
    void getBillFailBecauseOfNotFound() {
        //@formatter:off
        given().
        when().
                get("/bills/{id}", Integer.MAX_VALUE).
        then().
                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }

    @Test
    void createBillSuccess() {
        //@formatter:off
        given().
                contentType(ContentType.JSON).
                body(new Bill(Date.from(Instant.now()), new Address("haakestraße", 55, "21075", "hamburg"), new Address("haakestraße", 40, "21075", "hamburg"))).
        when().
                post("/bills").
        then().
                statusCode(HttpStatus.CREATED.value());
                //body("customerId", is(greaterThan(0)));
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
    void deleteBillSuccess() {
        //@formatter:off
        given().
                delete("/bills/{id}", bill.getBillId()).
        then().
                statusCode(HttpStatus.OK.value());

        given().
        when().
                get("/bills/{id}", bill.getBillId()).
        then().
                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }
}