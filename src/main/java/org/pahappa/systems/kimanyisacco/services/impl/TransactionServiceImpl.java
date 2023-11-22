package org.pahappa.systems.kimanyisacco.services.impl;


import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.dao.TransactionDAO;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private Transaction transaction;
    private Account account;
    private MemberService memberService;
    private AccountService accountService;
    Date currentDate = new Date();
    public TransactionServiceImpl(){
        this.transaction = new Transaction();
        this.account = new Account();
        this.memberService = new MemberServiceImpl();
        this.accountService = new AccountServiceImpl();
    }
    @Override
    public List<Transaction> getTransaction(){
        return TransactionDAO.getAllTransactions();
    }
    @Override
    public void deposit(double depositAmount){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Long currentMemberId = (Long) session.getAttribute("member");
        Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
        transaction.setTransactionAmount(depositAmount);
        transaction.setTransactionType("Deposit");
        transaction.setDateOfTransaction(currentDate);
        transaction.setAccount(currentAccount);
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        TransactionDAO.saveTransaction(transaction);
    }
    @Override
    public Transaction getTransactionById(long transactionId){
        return TransactionDAO.getTransactionById(transactionId);
    }
    @Override
    public void rejectWithdraw(Transaction transaction){
        double withdrawalAmount = transaction.getTransactionAmount();
        transaction.setTransactionStatus(TransactionStatus.REJECTED);
        transaction.setDateOfTransaction(currentDate);
        transaction.setWithdrawNotification("Your withdraw of " + withdrawalAmount + " was rejected");
        TransactionDAO.approveStatus(transaction);
    }
    @Override
    public void acceptWithdraw(Transaction transaction){
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        transaction.setDateOfTransaction(currentDate);
        transaction.setWithdrawNotification("Your withdraw of " + transaction.getTransactionAmount() +" was accepted");
        TransactionDAO.approveStatus(transaction);
    }
    @Override
    public void withdraw(double withdrawalAmount){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Long currentMemberId = (Long) session.getAttribute("member");
        Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
        System.out.println(currentDate);
        transaction.setTransactionAmount(withdrawalAmount);
        transaction.setTransactionType("Withdraw");
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setDateOfTransaction(currentDate);
        transaction.setAccount(currentAccount);
        TransactionDAO.saveTransaction(transaction);
    }

}
