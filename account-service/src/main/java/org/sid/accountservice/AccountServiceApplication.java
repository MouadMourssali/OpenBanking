package org.sid.accountservice;

import org.sid.accountservice.clients.CustomerRestClient;
import org.sid.accountservice.entities.BankAccount;
import org.sid.accountservice.entities.Transaction;
import org.sid.accountservice.enums.AccountType;
import org.sid.accountservice.repositories.BankAccountRepository;
import org.sid.accountservice.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
    //@Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient, TransactionRepository transactionRepository){
        return args -> {
            customerRestClient.allCustomers().forEach(customer -> {
                // Créer et sauvegarder les comptes bancaires
                BankAccount bankAccount = BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .currency("MAD")
                        .balance(Math.random() * 80000)
                        .createAt(LocalDate.now())
                        .type(Math.random()>0.5?AccountType.SAVING_ACCOUNT:AccountType.CURRENT_ACCOUNT)
                        .customerId(customer.getId())
                        .build();

                bankAccountRepository.save(bankAccount);

                // Créer et sauvegarder des transactions pour chaque compte bancaire
                Transaction transaction = Transaction.builder()
                        .amount(Math.random() * 10000)
                        .transactionTime(LocalDateTime.now())
                        .description("none")
                        .bankAccount(bankAccount)
                        .build();
                transactionRepository.save(transaction);

            });
        };
    }

}
