package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transaction_serial", columnList = "tokenSerial,transactionSerial"),
        @Index(name = "idx_transaction_expires", columnList = "expiresAt")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date expiresAt;

    private String tokenSerial;

    private String transactionSerial;

    public Transaction(Date expiresAt, String tokenSerial, String transactionSerial) {
        this.expiresAt = expiresAt;
        this.tokenSerial = tokenSerial;
        this.transactionSerial = transactionSerial;
    }
}
