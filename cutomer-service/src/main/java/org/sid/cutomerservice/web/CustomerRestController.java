package org.sid.cutomerservice.web;
import org.sid.cutomerservice.entities.Customer;
import org.sid.cutomerservice.repositories.CustomerRepository;
import org.sid.cutomerservice.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    public CustomerRestController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerRestController.class);
    @GetMapping("/customers")
    public List<Customer>customerList(){
        LOGGER.info("Customer find : {}");
        return customerRepository.findAll();
    }
    @GetMapping("/customers/{id}")
    public Customer customerById(@PathVariable Long id){
        LOGGER.info("Customer find : id = {}",id);
        return customerRepository.findById(id).get();
    }
    @PostMapping("/addcustomer")
    public Customer addCustomer(@RequestBody Customer customer){
        LOGGER.info("Customer add : {}",customer);
        return customerService.addCustomer(customer);
    }
}
