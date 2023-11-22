package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransaction();
    Transaction getTransactionById(long transactionId);
    void rejectWithdraw(Transaction transaction);
    void acceptWithdraw(Transaction transaction);
    void deposit(double depositAmount);
    void withdraw(double withdrawalAmount);
}
