package org.sid.cutomerservice.services;

import org.sid.cutomerservice.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private List<Customer> customerList=new ArrayList<>();
    public Customer addCustomer(Customer customer){
        customerList.add(customer);
        return customer;
    }

}
