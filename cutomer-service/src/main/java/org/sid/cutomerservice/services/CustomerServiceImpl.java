package org.sid.cutomerservice.services;

import org.sid.cutomerservice.dao.CustomerRepository;
import org.sid.cutomerservice.dto.CustomerDto;
import org.sid.cutomerservice.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);


    /* public Customer updateCustomer(Long customerId,Customer customer) throws CustomerNotExistException {
        Customer customer1 = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistException("Customer not found with ID: " + customerId));
        customer1.setEmail(customer.getEmail());
        customer1.setName(customer.getName());

        return customer1;
    }



    public String publishProductMessage(Customer customer) {
        this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);
        this.rabbitMessagingTemplate.convertAndSend(ProducerMicroserviceApplication.PRODUCT_EXCHANGE, ProducerMicroserviceApplication.PRODUCT_ROUTING_KEY,customer);
        return "Customer Message Published";
    }*/

    @Override
    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public void update(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Override
    public void delete(long id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto getCustomer(long id) {
        Customer customerBO = this.customerRepository.findById(id).get();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerBO.getId());
        customerDto.setName(customerBO.getName());
        customerDto.setEmail(customerBO.getEmail());
        return customerDto;
    }


}
