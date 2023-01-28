package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.exceptions.CustomerAlreadyExistingException;
import com.haw.srs.customerservice.exceptions.CustomerNotFoundException;
import com.haw.srs.customerservice.exceptions.OrderNotFoundException;
import com.haw.srs.customerservice.exceptions.ProductNotFoundException;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.services.ProductService;
import com.haw.srs.customerservice.wrapperklassen.ProductIdUpdateProductWrapper;
import com.haw.srs.customerservice.wrapperklassen.ProductUsernameWrapper;
import com.haw.srs.customerservice.wrapperklassen.UsernameProductIDWrapper;
import com.haw.srs.customerservice.wrapperklassen.UsernameUpdateCustomerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/products")
public class ProductFacade {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ProductFacade(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * A method for getting all products in the database
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    /**
     * A method for getting a specific product
     * in the database corresponding to an id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id" + "/{id:[\\d]+}")
    public List<Product> getProductById(@PathVariable("id") Long productId) throws ProductNotFoundException {
        List<Product> suchergebnis = new ArrayList<>();
        Product product = productRepository.findByProductId(productId).orElseThrow(()-> new ProductNotFoundException(productId));
        suchergebnis.add(product);
        return suchergebnis;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getownerof" + "/{id:[\\d]+}")
    public List<Customer> getProductOwner(@PathVariable("id") Long productId) throws ProductNotFoundException, CustomerNotFoundException {
        List<Customer> suchergebnis = new ArrayList<>();
        Product product = productRepository.findByProductId(productId).
                orElseThrow(()-> new ProductNotFoundException(productId));
        suchergebnis.add(customerRepository.findById(product.getOwnerId()).
                orElseThrow(() -> new CustomerNotFoundException(product.getOwnerId())));
        return suchergebnis;
    }

    /**
     * A method for getting all products in the database
     * when an empty string is entered int he search bar
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/suche/")
    public List<Product> getProductsbySearchEmpty() {
        // name =name.replace("_", " ");
        List<Product> suchergebnis = new ArrayList<>();
        for (Product product : productRepository.findAll()) {

                suchergebnis.add(product);

        }
        return suchergebnis;
    }

    /**
     * A method for getting specific products in the database
     * corresponding to a name enrtered in the search bar
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/suche"+"/{name}")
    public List<Product> getProductsbySearch(@PathVariable("name") String name) {
        // name =name.replace("_", " ");
        List<Product> suchergebnis = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.getName().matches("(?i).*" + name + ".*")) {
                suchergebnis.add(product);
            } else if(name.length() == 0){
                suchergebnis.add(product);
            }
        }
        return suchergebnis;
    }

    /**
     * A method for sorting all products in the database
     * alphabetically, by price and date of listing
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/sortBy" + "{value}")
    public List<Product> getProductsByOption(@PathVariable("value") String value) {
        List<Product> suchergebnis = productRepository.findAll();
        switch (value){
            case "Preis":
                suchergebnis.sort(Comparator.comparing(Product::getPreis));
                break;
            case "Erstellungsdatum":
                suchergebnis.sort(Comparator.comparing(Product::getErstellungsdatum));
                break;
            case "Name":
                suchergebnis.sort(Comparator.comparing(Product::getName));
                break;
            default:
                suchergebnis.sort(Comparator.comparing(Product::getProductId));
        }
        return suchergebnis;
    }

    /**
     * A method for getting all products in the database
     * corresponding to an specific category
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/searchByCategory" + "{value}")
    public List<Product> getProductsByCategory(@PathVariable("value") String value) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> suchergebnis = new ArrayList<>();
        for (Product product: allProducts){
            if (product.getProductCategory().toString().toLowerCase().equals(value)){
                suchergebnis.add(product);
            }
        }
        return suchergebnis;
    }

    /**
     * A method for deleting a specific product
     * in the listed products of a specific user
     */
    @DeleteMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestBody UsernameProductIDWrapper usernameProductIDWrapper) throws ProductNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findByUsername(usernameProductIDWrapper.getUsername()).
                orElseThrow(() -> new CustomerNotFoundException(usernameProductIDWrapper.getUsername()));
        Product product = productRepository.findByProductId(usernameProductIDWrapper.getProductid()).
                orElseThrow(() -> new ProductNotFoundException(usernameProductIDWrapper.getProductid()));
        customer.removePlacedProduct(product);

        productRepository.delete(product);
    }

    //  @PostMapping
    //  @ResponseStatus(HttpStatus.CREATED)
    //  public Product createProduct(@RequestBody Product product) {
    //      return productRepository.save(product);
    //  }

    /**
     * A method for creating(listing) a product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductUsernameWrapper productUsernameWrapper) throws CustomerNotFoundException, ProductNotFoundException {
        Customer customer = customerRepository.findByUsername(productUsernameWrapper.getUsername())
                .orElseThrow(() -> new CustomerNotFoundException(productUsernameWrapper.getUsername()));
        Product currentproduct = productUsernameWrapper.getProduct();
        currentproduct.setOwnerId(customer.getCustomerId());
        Product product = productRepository.save(productUsernameWrapper.getProduct());

        customer.addPlacedProduct(product);

        customerRepository.save(customer);

        return productRepository.findById(productUsernameWrapper.getProduct().getProductId()).
                orElseThrow(() -> new ProductNotFoundException(productUsernameWrapper.getProduct().getProductId()));
    }


    /**
     * A method for updating a listed product
     * corresponding to an specific user
     */

    @PutMapping
    public Product updateProduct(@RequestBody ProductIdUpdateProductWrapper productIdUpdateProductWrapper) throws ProductNotFoundException {
        Product productToUpdate= productRepository
                .findByProductId(productIdUpdateProductWrapper.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(productIdUpdateProductWrapper.getProductId()));

        Product newProductData = productIdUpdateProductWrapper.getProduct();

        if(newProductData.getName()!=null){
            productToUpdate.setName(newProductData.getName());
        }
        if(newProductData.getPreis()!=null){
            productToUpdate.setPreis(newProductData.getPreis());
        }
        if(productToUpdate.getProductCategory()!=null){
            productToUpdate.setProductCategory(newProductData.getProductCategory());
        }
        if(productToUpdate.getBeschreibung()!=null){
            productToUpdate.setBeschreibung(newProductData.getBeschreibung());
        }
        if (productToUpdate.getProductImage()!=null){
            productToUpdate.setProductImage(newProductData.getProductImage());
        }
        return productRepository.save(productToUpdate);
    }
}
