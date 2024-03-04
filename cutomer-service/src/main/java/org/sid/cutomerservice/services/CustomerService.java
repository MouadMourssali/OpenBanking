package org.sid.cutomerservice.services;


import org.sid.cutomerservice.dto.CustomerDto;
import org.sid.cutomerservice.entities.Customer;


public interface CustomerService{
    Customer save(Customer customer);

    void update(Customer customer);

    void delete(long id);

   CustomerDto getCustomer(long id);
}
