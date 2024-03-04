package org.sid.cutomerservice.web;

import org.sid.cutomerservice.dto.CustomerDto;
import org.sid.cutomerservice.entities.Customer;
import org.sid.cutomerservice.event.CustomerEvent;
import org.sid.cutomerservice.services.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController  implements ApplicationEventPublisherAware {
    @Autowired
    private CustomerServiceImpl customerService;

    protected ApplicationEventPublisher eventPublisher;
    private static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);

    @PostMapping("/customer")
    public ResponseEntity<?>  addCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.save(customer);
        CustomerDto customerDto = this.customerService.getCustomer(savedCustomer.getId());
        CustomerEvent customerEvent = new CustomerEvent(this, "CustomerEvent", customerDto);
        eventPublisher.publishEvent(customerEvent);

        return ResponseEntity.ok().body("The new customer has been saved with ID: "
                + savedCustomer.getId());
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
