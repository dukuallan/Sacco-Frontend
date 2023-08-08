package org.pahappa.systems.kimanyisacco.models;

import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    private long transactionId;
    private double transactionAmount;
    private String transactionType;
    private Date dateOfTransaction;
    private String WithdrawNotification;
    private TransactionStatus transactionStatus;
    private Account account;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    public long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = "transaction_amount", nullable = false)
    public double getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Column(name = "transaction_type", nullable = false)
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "transaction_date", nullable = false)
    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }
    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    @Column(name = "withdraw_notification", nullable = true)
    public String getWithdrawNotification() {
        return WithdrawNotification;
    }
    public void setWithdrawNotification(String withdrawNotification) {
        this.WithdrawNotification = withdrawNotification;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false, length = 50)
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

}
