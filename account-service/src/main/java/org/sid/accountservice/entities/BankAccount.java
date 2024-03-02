package org.sid.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.sid.accountservice.enums.AccountType;
import org.sid.accountservice.model.Customer;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter@Setter@ToString@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private LocalDate createAt;
    private String currency;
    @Enumerated(EnumType.ORDINAL)
    private AccountType type;
    @Transient
    @JsonBackReference
    private Customer customer;
    private Long customerId;
    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactions;
}