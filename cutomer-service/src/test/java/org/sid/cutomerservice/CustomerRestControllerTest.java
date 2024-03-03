package org.sid.cutomerservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sid.cutomerservice.entities.Customer;
import org.sid.cutomerservice.repositories.CustomerRepository;
import org.sid.cutomerservice.services.CustomerService;
import org.sid.cutomerservice.web.CustomerRestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerRestControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerRestController customerRestController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCustomerList() {
        // Given
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "mouad", "mouad@example.com"),
                new Customer(2L, "Alice", "alice@example.com")
        );
        when(customerRepository.findAll()).thenReturn(customers);


        List<Customer> result = customerRestController.customerList();


        assertEquals(customers.size(), result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testCustomerById() {
        // Given
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "mouad", "mouad@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));


        Customer result = customerRestController.customerById(customerId);


        assertEquals(customer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testAddCustomer() {
        // Given
        Customer customer = new Customer(1L, "mouad", "mouad@example.com");
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

        // When
        Customer result = customerRestController.addCustomer(customer);

        // Then
        assertEquals(customer, result);
        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }
}
