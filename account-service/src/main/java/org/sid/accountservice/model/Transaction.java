package org.sid.accountservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Transaction {
    private String accountId;
    private double amount;

    // Getters and setters
}
