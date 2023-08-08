package org.pahappa.systems.kimanyisacco.views.dashboard;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "memberTransactionRecords")
@SessionScoped
public class MemberTransactionRecordsBean {
    private Member member;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;
    private TransactionService transactionService;
    private AccountService accountService;

    public MemberTransactionRecordsBean() {
        this.member = new Member();
        this.account = new Account();
        this.transaction = new Transaction();
        this.transactionService = new TransactionServiceImpl();
        this.accountService = new AccountServiceImpl();
    }

    public List<Transaction> displayUserTransactionRecords() throws Exception {
        transactionList = transactionService.getTransaction();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");

            if (currentMemberId != null) {
                // Create a new list to store filtered transactions with similar account IDs
                List<Transaction> filteredTransactions = new ArrayList<>();
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if(currentAccount != null){
                    // Iterate through the transactionsList to find transactions associated with the currentAccountId
                    for (Transaction transaction : transactionList) {
                        if (transaction.getAccount().getAccountId() == currentAccount.getAccountId()) {
                            filteredTransactions.add(transaction);
                        }
                    }
                    if (!filteredTransactions.isEmpty()) {
                        return filteredTransactions;
                    }
                }else {
                    return  null;
                }
            }
        }
        return  null;
    }

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
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
