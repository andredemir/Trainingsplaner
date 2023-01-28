package com.haw.srs.customerservice.facades;

import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Order;
import com.haw.srs.customerservice.entities.Product;
import com.haw.srs.customerservice.exceptions.*;
import com.haw.srs.customerservice.repositories.BillRepository;
import com.haw.srs.customerservice.repositories.CustomerRepository;
import com.haw.srs.customerservice.entities.Customer;
import com.haw.srs.customerservice.repositories.ProductRepository;
import com.haw.srs.customerservice.services.CustomerService;
import com.haw.srs.customerservice.services.ProductService;
import com.haw.srs.customerservice.wrapperklassen.UsernameProductIDWrapper;
import com.haw.srs.customerservice.wrapperklassen.UsernameUpdateCustomerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/customers")
public class CustomerFacade {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final BillRepository billRepository;
    @Autowired
    public CustomerFacade(CustomerRepository customerRepository, ProductRepository productRepository, BillRepository billRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.billRepository = billRepository;
    }

    /**
     * A method for getting all customers in the database
     */
    @GetMapping
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }
    // SCHLECHTE METHODE
   // @GetMapping(value = "/{id:[\\d]+}")
   // public Customer getCustomer(@PathVariable("id") Long customerId) throws CustomerNotFoundException {
   //     return customerRepository
   //             .findById(customerId)
   //             .orElseThrow(() -> new CustomerNotFoundException(customerId));
   // }

    /**
     * A method for getting a specific customer corresponding to a name
     */
    @GetMapping(value = "/username:{name}")
    public Customer getCustomerByName(@PathVariable("name") String name) throws CustomerNotFoundException {
        return customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
    }

    /**
     * A method for getting a list of customers corresponding to a username
     */
    @GetMapping(value = "/usernamelist:{name}")
    public List<Customer> getCustomerListByName(@PathVariable("name") String name) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        return customers;
    }

    /**
     * A method for getting a specific customer corresponding to an id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/customerbyid:{id}")
    public List<Customer> getCustomerListByID(@PathVariable("id") long id) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        return customers;
    }

    /**
     * A method for getting all listed products of a
     * specific customer corresponding to a username
     */
    @GetMapping(value = "/productsfrom:{name}")
    public List<Product> getProductsbyUsername(@PathVariable("name") String name) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
        return customer.getPlacedProducts();
    }

    /**
     * A method for getting all products in the cart
     * of a specific customer corresponding to a username
     */
    @GetMapping(value = "/cartfrom:{name}")
    public List<Product> getCartbyUsername(@PathVariable("name") String name) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
        return customer.getProducts();
    }

    /**
     * A method for getting all bills of a specific
     * customer corresponding to a username
     */
    @GetMapping(value = "/billsfrom:{name}")
    public List<Bill> getBillsByUsername(@PathVariable("name") String name) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
        return customer.getBills();
    }

    /**
     * A method for getting the user of an prodcutID
     */
    @GetMapping(value = "/getcustomerbyproductid:{productID}")
    public List<Customer> getCustomerByProductID(@PathVariable("productID") Long productId) throws CustomerNotFoundException, ProductNotFoundException {
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Long ownerId = product.getOwnerId();
        Customer customer = customerRepository
                .findById(ownerId)
                .orElseThrow(() -> new CustomerNotFoundException(ownerId));
        List<Customer> ownerOfProduct = new ArrayList<>();
        ownerOfProduct.add(customer);
        return ownerOfProduct;
    }

    /**
     * A method for getting the user of an prodcutID
     */
    @GetMapping(value = "/getallproductswithsameownerid:{productID}")
    public List<Product> getAllProductsFromOwnerId(@PathVariable("productID") Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByProductId(productId).orElseThrow(()-> new ProductNotFoundException(productId));
        Long ownerId = product.getOwnerId();
        List<Product> allProducts = productRepository.findAll();
        List<Product> ownerProducts = new ArrayList<>();
        for (Product p: allProducts) {
            if (Objects.equals(p.getOwnerId(), ownerId)){
                ownerProducts.add(p);
            }
        }
        return ownerProducts;
    }

    /**
     * A method for ordering all products in the cart
     * of a specific customer corresponding to a username
     * and generating the bill
     */
    @PostMapping(value = "/buycartfrom:{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public String buyCart(@PathVariable("name") String name) throws CustomerNotFoundException, ProductNotFoundException, EmptyCartException {
        Bill bill = new Bill(1);

        Customer customer = customerRepository
                .findByUsername(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
        List<Product> alleProdukte = customer.getProducts();
        List<Long> alleProdukteIDs = new ArrayList<>();

        for (Product product : alleProdukte) {
            alleProdukteIDs.add(product.getProductId());
        }
        if (alleProdukteIDs.size() == 0){
            throw new EmptyCartException(customer.getCustomerId());
        }
        bill.setBillContent("RECHNUNG:");
        bill.setTotalPrice(0.0);
        for (Long productid : alleProdukteIDs) {
            Product currentProduct = productRepository.findByProductId(productid).
                    orElseThrow(() -> new ProductNotFoundException(productid));
            bill.setBillContent(bill.getBillContent() + "\n------------ Produkt: ------------\n" + currentProduct.toString());
            bill.setTotalPrice(bill.getTotalPrice() + currentProduct.getPreis());
          //  Product currentProduct = productRepository.
          //          findById(product.getProductId()).
          //          orElseThrow(() -> new ProductNotFoundException(product.getProductId()));
            Customer owner = customerRepository.findById(currentProduct.getOwnerId()).
                    orElseThrow(() -> new CustomerNotFoundException(currentProduct.getOwnerId()));
            owner.removePlacedProduct(currentProduct);
            customer.removeProduct(currentProduct);

            productRepository.delete(currentProduct);
            customerRepository.save(owner);
            customerRepository.save(customer);
        }

        bill.setBillContent(bill.getBillContent() +"\n-------------------\n Gesamtpreis: \n" + bill.getTotalPrice());
        billRepository.save(bill);
        customer.addBill(bill);
        customerRepository.save(customer);

        return bill.getBillContent();
    }

    /**
     * A method for deleting a specific customer
     * corresponding to an id from the database
     */
    @DeleteMapping("/{id:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable("id") Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customerRepository.delete(customer);
    }

    /**
     * A method for creating a new customer in the database
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * A method for updating the personal information
     * of a specific customer corresponding to a username
     * (the username of the currently logged user)
     */
    @PutMapping
    public Customer updateCustomer(@RequestBody UsernameUpdateCustomerWrapper usernameUpdateCustomerWrapper) throws CustomerNotFoundException, CustomerAlreadyExistingException {
        Customer customerToUpdate = customerRepository
                .findByUsername(usernameUpdateCustomerWrapper.getUsername())
                .orElseThrow(() -> new CustomerNotFoundException(usernameUpdateCustomerWrapper.getCustomer().getUsername()));

        Customer newCustomerData = usernameUpdateCustomerWrapper.getCustomer();

        if(newCustomerData.getAddress()!=null){
            customerToUpdate.setAddress(newCustomerData.getAddress());
        }
        if(newCustomerData.getFirstName()!=null){
            customerToUpdate.setFirstName(newCustomerData.getFirstName());
        }
        if(newCustomerData.getLastName()!=null){
            customerToUpdate.setLastName(newCustomerData.getLastName());
        }
        if(newCustomerData.getPhoneNumber()!=null){
            customerToUpdate.setPhoneNumber(newCustomerData.getPhoneNumber());
        }
        return customerRepository.save(customerToUpdate);
    }

    /**
     * A method for adding a product in the cart
     * of a specific customer corresponding to a username
     * (the username of the currently logged user)
     */
    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addProductToCart(@RequestBody UsernameProductIDWrapper usernameProductIDWrapper) throws CustomerNotFoundException, ProductNotFoundException, ProductAlreadyInCartException, ProductIsYourOwnException {
        Customer customer = customerRepository.findByUsername(usernameProductIDWrapper.getUsername()).
                orElseThrow(() -> new CustomerNotFoundException(usernameProductIDWrapper.getUsername()));
        Product product = productRepository.findByProductId(usernameProductIDWrapper.getProductid()).
                orElseThrow(() -> new ProductNotFoundException(usernameProductIDWrapper.getProductid()));
        if (!customer.getPlacedProducts().contains(product)){
            if (!product.isInCart()){
                customer.addProduct(product);
            } else {
                throw new ProductAlreadyInCartException(customer.getCustomerId(), usernameProductIDWrapper.getProductid());
            }
        } else {
            throw new ProductIsYourOwnException(customer.getCustomerId(), usernameProductIDWrapper.getProductid());
        }

        return customerRepository.save(customer);
    }

    /**
     * A method for deleting a product from the cart
     * of a specific customer corresponding to a username
     * (the username of the currently logged user)
     */
    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer removeProductFromCart(@RequestBody UsernameProductIDWrapper usernameProductIDWrapper) throws CustomerNotFoundException, ProductNotFoundException {
        Customer customer = customerRepository.findByUsername(usernameProductIDWrapper.getUsername()).
                orElseThrow(() -> new CustomerNotFoundException(usernameProductIDWrapper.getUsername()));
        Product product = productRepository.findByProductId(usernameProductIDWrapper.getProductid()).
                orElseThrow(() -> new ProductNotFoundException(usernameProductIDWrapper.getProductid()));
        customer.removeProduct(product);
        return customerRepository.save(customer);
    }
}
