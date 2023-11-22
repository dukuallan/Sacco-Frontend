package org.pahappa.systems.kimanyisacco.views.dashboard;

import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@ManagedBean(name = "memberDashboard")
@SessionScoped
public class MemberDashboardBean {
    private Member member;
    private MemberService memberService;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;
    private TransactionService transactionService;
    private AccountService accountService;


    public MemberDashboardBean() {
        this.member = new Member();
        this.account = new Account();
        this.transaction = new Transaction();
        this.memberService = new MemberServiceImpl();
        this.transactionService = new TransactionServiceImpl();
        this.accountService = new AccountServiceImpl();
    }

    public double getMemberBalance(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                // method to fetch the current account based on the member ID
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if(currentAccount != null){
                    return currentAccount.getBalance();
                }else {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }
    public double getMemberDeposits(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                //fetch the current account based on the member ID
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if(currentAccount != null){
                    transactionList = transactionService.getTransaction();
                    double totalDeposit = 0.0;
                    for(Transaction trans : transactionList){
                        if(trans.getTransactionType().contains("Deposit") && trans.getAccount().getAccountId() == currentAccount.getAccountId()){
                            totalDeposit += trans.getTransactionAmount();
                        }
                    }
                    return totalDeposit;
                }else {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }
    public double getMemberWithdraws(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                //fetch the current account based on the member ID
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if(currentAccount !=null){
                    transactionList = transactionService.getTransaction();
                    double totalWithdraw = 0.0;
                    for(Transaction trans : transactionList){
                        if(trans.getTransactionType().contains("Withdraw")  && trans.getAccount().getAccountId() == currentAccount.getAccountId() && trans.getTransactionStatus().equals(TransactionStatus.APPROVED)){
                            totalWithdraw += trans.getTransactionAmount();
                        }
                    }
                    return totalWithdraw;
                }else {
                    return  0.0;
                }
            }
        }
        return 0.0;
    }
    public int getDepositCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                // method to fetch the current account based on the member ID
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if(currentAccount != null){
                    transactionList = transactionService.getTransaction();
                    int depositCount = 0;
                    for (Transaction trans : transactionList) {
                        if (trans.getTransactionType().contains("Deposit") && trans.getAccount().getAccountId() == currentAccount.getAccountId()) {
                            depositCount++;
                        }
                    }
                    return depositCount;
                }else {
                    return  0;
                }

            }
        }
        return 0;
    }
    public int getWithdrawCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            System.out.println(currentMemberId);
            if (currentMemberId != null) {
                // to fetch the current account based on the member ID
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                System.out.println(currentAccount);
                if(currentAccount != null){
                    transactionList = transactionService.getTransaction();
                    int withdrawCount = 0;
                    for (Transaction trans : transactionList) {
                        if (trans.getTransactionType().contains("Withdraw") && trans.getAccount().getAccountId() == currentAccount.getAccountId() && trans.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
                            withdrawCount++;
                        }
                    }
                    return withdrawCount;
                }else {
                    return 0;
                }

            }
        }
        return 0;
    }
    public int getNotificationCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            System.out.println(currentMemberId);
            if (currentMemberId != null) {
                // to fetch the current account based on the account ID

                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                System.out.println(currentAccount);
                if(currentAccount != null){
                    transactionList = transactionService.getTransaction();
                    int withdrawCount = 0;
                    for (Transaction trans : transactionList) {
                        if (trans.getTransactionType().contains("Withdraw") && trans.getAccount().getAccountId() == currentAccount.getAccountId()) {
                            withdrawCount++;
                        }
                    }
                    return withdrawCount;
                }else {
                    return 0;
                }
            }
        }
        return 0;
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
