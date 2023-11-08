package com.noty.web.repsitories;

import com.noty.web.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.tokenSerial = :serial AND t.transactionSerial = :transaction ")
    Transaction findTransaction(String serial, String transaction);

    @Modifying
    @Query("DELETE FROM Transaction WHERE expiresAt < :now")
    void deleteExpired(Date now);
}
