package com.noty.web.services.implementation;

import com.noty.web.components.DateTime;
import com.noty.web.entities.Transaction;
import com.noty.web.repsitories.TransactionRepository;
import com.noty.web.services.TrackingResult;
import com.noty.web.services.TransactionTracker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionTrackerImpl implements TransactionTracker {

    private final TransactionRepository repository;

    private final DateTime dateTime;

    @Override
    public synchronized TrackingResult trackTransaction(String tokenSerial, String transactionSerial) {
        Transaction transaction = repository.findTransaction(tokenSerial, transactionSerial);
        if (transaction != null)
            return TrackingResult.FOUND;

        transaction = new Transaction(
                dateTime.now(),
                tokenSerial,
                transactionSerial
        );
        repository.save(transaction);
        return TrackingResult.CREATED;
    }

    @Override
    public synchronized void purgeTransactions() {
        Date now = dateTime.now();
        repository.deleteExpired(now);
    }
}
