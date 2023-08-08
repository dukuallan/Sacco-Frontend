package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "withdrawApproval")
@SessionScoped
public class WithdrawApprovalBean {
    private TransactionService transactionService;
    private AccountService accountService;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;

    public WithdrawApprovalBean(){
        this.account = new Account();
        this.transactionService = new TransactionServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.transaction = new Transaction();
    }

    public List<Transaction> displayPendingWithdrawalRequests() {
        transactionList = transactionService.getTransaction();
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    public void acceptWithdrawalRequest(long transactionId, double withdrawalAmount) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        System.out.println(transaction);
        if (transaction != null) {
            if ("Withdraw".equals(transaction.getTransactionType()) && transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) {
                Account account = accountService.getAccountById(transaction.getAccount().getAccountId());
                if (account != null) {
                    // methods call
                    transactionService.acceptWithdraw(transaction);
                    accountService.withdrawAmount(account.getAccountId(),withdrawalAmount);
                }
            }
        }
    }
    public void rejectWithdrawalRequest(long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction != null) {
            if ("Withdraw".equals(transaction.getTransactionType()) && transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) {
                // method call
                transactionService.rejectWithdraw(transaction);
            }
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
