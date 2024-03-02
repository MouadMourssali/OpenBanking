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
    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient, TransactionRepository transactionRepository){
        return args -> {
            customerRestClient.allCustomers().forEach(customer -> {
                // Créer et sauvegarder les comptes bancaires
                BankAccount bankAccount1 = BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .currency("MAD")
                        .balance(Math.random() * 80000)
                        .createAt(LocalDate.now())
                        .type(AccountType.CURRENT_ACCOUNT)
                        .customerId(customer.getId())
                        .build();

                BankAccount bankAccount2 = BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .currency("MAD")
                        .balance(Math.random() * 68541)
                        .createAt(LocalDate.now())
                        .type(AccountType.SAVING_ACCOUNT)
                        .customerId(customer.getId())
                        .build();

                bankAccountRepository.save(bankAccount1);
                bankAccountRepository.save(bankAccount2);

                // Créer et sauvegarder des transactions pour chaque compte bancaire
                Transaction transaction1 = Transaction.builder()
                        .accountId(bankAccount1.getAccountId())
                        .amount(Math.random() * 10000)
                        .transactionTime(LocalDateTime.now())
                        .description("Transaction pour le compte bancaire 1")
                        .bankAccount(bankAccount1)
                        .build();

                Transaction transaction2 = Transaction.builder()
                        .accountId(bankAccount2.getAccountId())
                        .amount(Math.random() * 5000)
                        .transactionTime(LocalDateTime.now())
                        .description("Transaction pour le compte bancaire 2")
                        .bankAccount(bankAccount2)
                        .build();

                transactionRepository.save(transaction1);
                transactionRepository.save(transaction2);
            });
        };
    }

}
