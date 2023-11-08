package com.noty.web.services.implementation;

import com.noty.web.NotyException;
import com.noty.web.components.DateTime;
import com.noty.web.entities.Transaction;
import com.noty.web.repsitories.TransactionRepository;
import com.noty.web.services.TrackingResult;
import com.noty.web.services.TransactionTracker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionTrackerImpl implements TransactionTracker {

    private final TransactionRepository repository;
    private final DateTime dateTime;
    private final long TRANSACTION_TTL = 24 * 60 * 60 * 1000;

    @Override
    public synchronized TrackingResult trackTransaction(String tokenSerial, String transactionSerial) {
        Transaction transaction = repository.findTransaction(tokenSerial, transactionSerial);
        if (transaction != null)
            return TrackingResult.FOUND;

        Date now = dateTime.now();
        Date expires = new Date(now.getTime() + TRANSACTION_TTL);

        transaction = new Transaction(
                now,
                tokenSerial,
                transactionSerial
        );
        transaction.setExpiresAt(expires);
        repository.save(transaction);
        return TrackingResult.CREATED;
    }

    @Override
    @Transactional
    public synchronized void purgeTransactions() throws NotyException {
        try {
            Date now = dateTime.now();
            repository.deleteExpired(now);

        } catch (Exception ex) {
            throw new NotyException("Failed to remove expired entries.", ex);

        }
    }
}
