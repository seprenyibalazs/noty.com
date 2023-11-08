package com.noty.web.services;

import com.noty.web.NotyException;

public interface TransactionTracker {

    TrackingResult trackTransaction(String tokenSerial, String transactionSerial);

    void purgeTransactions() throws NotyException;

}
