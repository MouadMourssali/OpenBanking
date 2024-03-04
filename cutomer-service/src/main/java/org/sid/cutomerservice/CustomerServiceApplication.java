package org.sid.cutomerservice;

import org.sid.cutomerservice.dto.CustomerDto;
import org.sid.cutomerservice.entities.Customer;
import org.sid.cutomerservice.event.CustomerEvent;
import org.sid.cutomerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationProperties(prefix="customer")
public class CustomerServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Autowired
    private CustomerService customerService;
    protected ApplicationEventPublisher eventPublisher;


    @PostMapping("/customer")
    public ResponseEntity<?> createAccount(@RequestBody Customer customer) {
        Customer savedCustomer = this.customerService.save(customer);
        CustomerDto customerDto = this.customerService.getCustomer(savedCustomer.getId());
        CustomerEvent customerCreatedEvent = new CustomerEvent(this, "CustomerCreatedEvent", customerDto);
        eventPublisher.publishEvent(customerCreatedEvent);

        return ResponseEntity.ok().body("The new customer "
                + savedCustomer.getId());
    }

}
