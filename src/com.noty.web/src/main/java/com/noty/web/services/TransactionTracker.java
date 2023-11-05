package com.noty.web.services;

public interface TransactionTracker {

    TrackingResult trackTransaction(String tokenSerial, String transactionSerial);

    void purgeTransactions();

}
