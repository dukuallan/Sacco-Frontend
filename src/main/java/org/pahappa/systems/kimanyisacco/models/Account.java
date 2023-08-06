package org.pahappa.systems.kimanyisacco.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    private long accountId;
    private double amount;
    private String bank;
    private String bankAccount;
    private String contact;
    private List<Transaction> transaction = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Column(name = "amount", nullable = false)
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "bank", nullable = false)
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }

    @Column(name = "bank_account", nullable = false)
    public String getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "contact", nullable = false)
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @OneToMany(mappedBy ="account" )
    public List<Transaction> getTransaction() {
        return transaction;
    }
    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                ", bank='" + bank + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", contact='" + contact + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}
