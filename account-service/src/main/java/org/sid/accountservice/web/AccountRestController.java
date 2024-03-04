package org.sid.accountservice.web;

import lombok.extern.slf4j.Slf4j;
import org.sid.accountservice.Exception.InsufficientException;
import org.sid.accountservice.clients.CustomerRestClient;
import org.sid.accountservice.entities.AmountRequest;
import org.sid.accountservice.entities.BankAccount;
import org.sid.accountservice.model.Customer;
import org.sid.accountservice.repositories.BankAccountRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Slf4j
public class AccountRestController {

    private BankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;

    public AccountRestController(RabbitTemplate rabbitTemplate, BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/accounts")
    public List<BankAccount>accountList() {
        List<BankAccount>accountList=bankAccountRepository.findAll();
        accountList.forEach(bankAccount -> {
            bankAccount.setCustomer(customerRestClient.findCustomerById(bankAccount.getCustomerId()));
        });
        return accountList;
    }
    @GetMapping("/accounts/{id}")
    public BankAccount bankAccountById(@PathVariable String id){
        BankAccount bankAccount= bankAccountRepository.findById(id).get();
        Customer customer=customerRestClient.findCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

    @PostMapping("/credit/{accountId}")
    public BankAccount creditAccount(@PathVariable String accountId, @RequestBody AmountRequest amountRequest) {
        double amount = amountRequest.getAmount();

        BankAccount bankAccount = bankAccountById(accountId);
        bankAccount.setBalance(bankAccount.getBalance() + amount);

        bankAccountRepository.save(bankAccount);
        log.info("Account Credit : {}", accountId);

        return bankAccount;
    }


    @PostMapping("/debit/{accountId}")
    public BankAccount DebitAccount(@PathVariable String accountId, @RequestBody AmountRequest amountRequest) throws InsufficientException {
        BankAccount bankAccount = bankAccountById(accountId);

        if (bankAccount.getBalance() < amountRequest.getAmount()) {
            throw new InsufficientException("Insufficient balance");
        } else {
            bankAccount.setBalance(bankAccount.getBalance() - amountRequest.getAmount());
            bankAccountRepository.save(bankAccount);
            log.info("Account Debit: {}", accountId);
            return bankAccount;
        }
    }

}
