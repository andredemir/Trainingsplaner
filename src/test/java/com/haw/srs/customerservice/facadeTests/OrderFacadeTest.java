package com.haw.srs.customerservice.facadeTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.datatypes.Address;
import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.enums.StatusType;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderFacadeTest {

    private final Log log = LogFactory.getLog(getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
     //   this.orderRepository.deleteAll();

        order = this.orderRepository.save(new Order(Date.from(Instant.now()), new Address("haakestraße", 40, "21075", "hamburg"), StatusType.OFFEN, null));

        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    void getAllOrdersSuccess() {
        //@formatter:off
        given().
                // add this here to log request --> log().all().
        when().
                get("/orders").
        then().
                // add this here to log response --> log().all().
                statusCode(HttpStatus.OK.value());
                //body(order.getDeliveryAddress().getStreet(), equalTo("haakestraße"));
        //@formatter:on
    }

    @Test
    void getOrdersSuccess() {
        //@formatter:off
        given().
        when().
                get("/orders/{id}", order.getOrderId()).
        then().
                statusCode(HttpStatus.OK.value());
                //body("name", equalTo("MacBook"));
        //@formatter:on
    }

    @Test
    void getOrderFailBecauseOfNotFound() {
        //@formatter:off
        given().
        when().
                get("/orders/{id}", Integer.MAX_VALUE).
        then().
                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }

    @Test
    void createOrderSuccess() {
        //@formatter:off
        given().
                contentType(ContentType.JSON).
                body(new Order(Date.from(Instant.now()), new Address("haakestraße", 40, "21075", "hamburg"), StatusType.OFFEN, null)).
        when().
                post("/orders").
        then().
                statusCode(HttpStatus.CREATED.value());
               // body("orderId", is(greaterThan(0)));
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
    void deleteOrderSuccess() {
        //@formatter:off
        given().
                delete("/orders/{id}", order.getOrderId()).
        then().
                statusCode(HttpStatus.OK.value());

        given().
        when().
                get("/orders/{id}", order.getOrderId()).
        then().
                statusCode(HttpStatus.NOT_FOUND.value());
        //@formatter:on
    }
}