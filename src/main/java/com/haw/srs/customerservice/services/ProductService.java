package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.enums.ProductCategory;
import com.haw.srs.customerservice.exceptions.ProductAlreadyExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * A service class of methods for Products
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

/*    @Transactional
    public Long createProduct(String name, String beschreibung, double preis, Date erstellungsdatum, ProductCategory productCategory) throws ProductAlreadyExistingException {
        Product product = new Product(name,beschreibung, preis, erstellungsdatum, productCategory);
        if (productRepository.findByProductId(product.getProductId()).isPresent()) {
            throw new ProductAlreadyExistingException(product.getProductId());
        }
        productRepository.save(product);
        return product.getProductId();
    }*/

    @Transactional
    public Long createProduct(String name, String beschreibung, double preis, Date erstellungsdatum, ProductCategory productCategory, String productImage) throws ProductAlreadyExistingException {
        Product product = new Product(name,beschreibung, preis, erstellungsdatum, productCategory, productImage);
        if (productRepository.findByProductId(product.getProductId()).isPresent()) {
            throw new ProductAlreadyExistingException(product.getProductId());
        }
        productRepository.save(product);
        return product.getProductId();
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> findBySearch(String suchtext){
        List<Product> suchergebnis = new ArrayList<>();
        for (Product product: findAllProducts() ) {
            if ((product.getName().matches("(?i).*"+ suchtext + ".*"))) {
                suchergebnis.add(product);
            }
        }
        return suchergebnis;
    }

    public List<Product> findByCategory(String category){
        List<Product> allProducts = productRepository.findAll();
        List<Product> suchergebnis = new ArrayList<>();
        for (Product product: allProducts){
            if (product.getProductCategory().toString().toLowerCase().equals(category)){
                suchergebnis.add(product);
            }
        }
        return suchergebnis;
    }

    public List<Product> sortByOption(String option){
        List<Product> sortedOutput = findAllProducts();
        switch (option){
            case "preis":
                sortedOutput.sort(Comparator.comparing(Product::getPreis));
            break;
            case "erstellungsdatum":
                sortedOutput.sort(Comparator.comparing(Product::getErstellungsdatum));
            break;
            case "name":
                sortedOutput.sort(Comparator.comparing(Product::getName));
            break;
        }
        return sortedOutput;
    }


    public List<Order> getProductsOrders(Long customerId){
        return customerRepository.getById(customerId).getOrders();
    }
}
