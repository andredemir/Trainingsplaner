package com.haw.srs.customerservice.serviceTests;

import com.haw.srs.customerservice.Application;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.exceptions.ProductAlreadyExistingException;
import com.haw.srs.customerservice.exceptions.ProductNotFoundException;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.OrderRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.services.CustomerService;
import com.haw.srs.customerservice.services.OrderService;
import com.haw.srs.customerservice.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        productRepository.deleteAll();
    }


    @Test
    void createProduct() throws ProductAlreadyExistingException {
        Long productId = productService.createProduct("Iphone 14","", 1600.00, null, ProductCategory.ELEKTRONIK, null);
        Assertions.assertTrue(productRepository.existsById(productId));
        Assertions.assertEquals(1, productRepository.findAll().size());

    }

    @Test
    void findBySearch() throws ProductAlreadyExistingException, ProductNotFoundException {
        Long productId = productService.createProduct("Apple Iphone 14","", 1600.00, null, ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService.createProduct("Apple MacBook Pro","ein Laptop aus 2018", 600.00, null, ProductCategory.ELEKTRONIK, null);
        Product iPhone = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Product macBook = productRepository.findByProductId(product2Id).orElseThrow(() -> new ProductNotFoundException(productId));

        List<Product> appleProductsFound = productService.findBySearch("apple");
        Assertions.assertTrue(appleProductsFound.contains(iPhone));
        Assertions.assertTrue(appleProductsFound.contains(macBook));
        Assertions.assertEquals(2, appleProductsFound.size());

        List<Product> iPhonesFound = productService.findBySearch("iphone");
        Assertions.assertTrue(iPhonesFound.contains(iPhone));
        Assertions.assertFalse(iPhonesFound.contains(macBook));
        Assertions.assertEquals(1, iPhonesFound.size());

    }

    @Test
    void findByCategory() throws ProductAlreadyExistingException, ProductNotFoundException {
        Long productId = productService.createProduct("Apple Iphone 14","", 1600.00, null, ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService.createProduct("Apple MacBook Pro","ein Laptop aus 2018", 600.00, null, ProductCategory.ELEKTRONIK, null);
        Product iPhone = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Product macBook = productRepository.findByProductId(product2Id).orElseThrow(() -> new ProductNotFoundException(productId));

        List<Product> elektronikProductsFound = productService.findByCategory("elektronik");
        Assertions.assertTrue(elektronikProductsFound.contains(iPhone));
        Assertions.assertTrue(elektronikProductsFound.contains(macBook));
        Assertions.assertEquals(2, elektronikProductsFound.size());
        System.out.println(elektronikProductsFound);
        elektronikProductsFound = productService.findByCategory("gustav");
        System.out.println(elektronikProductsFound);
    }

    @Test
    void sortByOption() throws ProductAlreadyExistingException, ProductNotFoundException {
        Long productId = productService.createProduct("Apple Iphone 14","", 1600.00, null, ProductCategory.ELEKTRONIK, null);
        Long product2Id = productService.createProduct("Apple MacBook Pro","ein Laptop aus 2018", 600.00, null, ProductCategory.ELEKTRONIK, null);
        Product iPhone = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Product macBook = productRepository.findByProductId(product2Id).orElseThrow(() -> new ProductNotFoundException(productId));

        //sortieren nach Preis
        List<Product> elektronikProductsFound = productService.sortByOption("preis");
        Assertions.assertEquals(elektronikProductsFound.get(0), macBook);

        //sortieren nach Name
        elektronikProductsFound = productService.sortByOption("name");
        Assertions.assertEquals(elektronikProductsFound.get(0), iPhone);
    }

}