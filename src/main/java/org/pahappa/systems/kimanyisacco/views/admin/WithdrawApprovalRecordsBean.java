package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "withdrawApprovalRecords")
@SessionScoped
public class WithdrawApprovalRecordsBean {
    private TransactionService transactionService;
    private Transaction transaction;
    private List<Transaction> transactionList;

    public WithdrawApprovalRecordsBean(){
        this.transactionService = new TransactionServiceImpl();
        this.transaction = new Transaction();
    }
    public List<Transaction> displayAllApprovalWithdrawRecords() {
        transactionList = transactionService.getTransaction();
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (!(transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) && transaction.getTransactionType().contains("Withdraw")) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
