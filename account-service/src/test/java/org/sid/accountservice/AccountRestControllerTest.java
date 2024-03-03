package org.sid.accountservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sid.accountservice.clients.CustomerRestClient;
import org.sid.accountservice.entities.BankAccount;
import org.sid.accountservice.model.Customer;
import org.sid.accountservice.repositories.BankAccountRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountRestControllerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRestClient customerRestClient;

    @InjectMocks
    private AccountRestController accountRestController;

    @Test
    public void testAccountList() {

        List<BankAccount> mockAccounts = new ArrayList<>();


        List<Customer> mockCustomers = new ArrayList<>();



        for (BankAccount account : mockAccounts) {
            String customerId = String.valueOf(account.getCustomerId());
            Customer customer = mockCustomers.stream()
                    .filter(c -> c.getId().equals(customerId))
                    .findFirst()
                    .orElse(null);
            when(customerRestClient.findCustomerById(Long.valueOf(customerId))).thenReturn(customer);
        }

        when(bankAccountRepository.findAll()).thenReturn(mockAccounts);


        List<BankAccount> result = accountRestController.accountList();


        assertEquals(mockAccounts.size(), result.size());
    }


}
