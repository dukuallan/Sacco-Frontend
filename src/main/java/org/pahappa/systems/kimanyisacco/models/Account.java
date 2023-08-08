package org.pahappa.systems.kimanyisacco.models;

import org.pahappa.systems.kimanyisacco.constants.BankName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    private long accountId;
    private double balance;
    private BankName bankName;
    private String bankAccountNumber;
    private Member member;
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

    @Column(name = "balance", nullable = false)
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_name", nullable = false, length = 50)
    public BankName getBankName() {
        return bankName;
    }
    public void setBankName(BankName bankName) {
        this.bankName = bankName;
    }

    @Column(name = "bank_account_number", nullable = false)
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    @OneToMany(mappedBy = "account")
    public List<Transaction> getTransaction() {
        return transaction;
    }
    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }


}