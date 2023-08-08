package org.pahappa.systems.kimanyisacco.services.impl;

import org.pahappa.systems.kimanyisacco.dao.AccountDAO;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private Account account;
    private Transaction transaction;
    private TransactionService transactionService;

    Date currentDate = new Date();

    public AccountServiceImpl(){
        this.account = new Account();
        this.transaction = new Transaction();
    }

    @Override
    public void createAccount(Account account){
        AccountDAO.save(account);
    }
    @Override
    public List<Account> getAllAccounts(){
        return AccountDAO.getAllAccounts();
    }
    @Override
    public Account getAccountById(long accountId){
        return AccountDAO.getAccountById(accountId);
    }

    @Override
    public Account getAccountByMemberId(long memberId){
        return AccountDAO.getAccountByMemberId(memberId);
    }
    @Override
    public void updateAccountAmount(long accountId, double depositAmount){
        Account currentAccount = getAccountById(accountId);
        double currentAmount = currentAccount.getBalance();
        double newBalance = currentAmount + depositAmount;
        AccountDAO.updateAccountAmount(currentAccount.getAccountId(), newBalance);
    }
    @Override
    public void withdrawAmount(long accountId, double withdrawalAmount){
        Account currentAccount = getAccountById(accountId);
        double currentBalance = currentAccount.getBalance();
        double newBalance = currentBalance - withdrawalAmount;
        AccountDAO.withdrawAmount(accountId,newBalance);
    }
}
