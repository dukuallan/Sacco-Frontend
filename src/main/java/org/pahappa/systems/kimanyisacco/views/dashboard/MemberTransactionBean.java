package org.pahappa.systems.kimanyisacco.views.dashboard;

import org.pahappa.systems.kimanyisacco.constants.BankName;
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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "memberTransaction")
@SessionScoped
public class MemberTransactionBean {
    private Member member;
    private MemberService memberService;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;

    private TransactionService transactionService;
    private AccountService accountService;
    public MemberTransactionBean() {
        this.member = new Member();
        this.account = new Account();
        this.transaction = new Transaction();
        this.memberService = new MemberServiceImpl();
        this.transactionService = new TransactionServiceImpl();
        this.accountService = new AccountServiceImpl();

    }
    public void makeDeposit(double depositAmount) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            System.out.println(currentMemberId);
            if (currentMemberId!= null) {
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                System.out.println(currentAccount);
                if (currentAccount != null) {
                    double currentAmount = currentAccount.getBalance();
                    if(depositAmount<=199){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "All deposit should be greater than 199"));
                        account.setBalance(0.0);
                        return;
                    }
                    // Update the account amount with the deposit
                    System.out.println(currentAccount);
                    System.out.println(depositAmount);
                    transactionService.deposit(depositAmount);
                    accountService.updateAccountAmount(currentAccount.getAccountId(), depositAmount);


                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Deposit", "Deposited Successfully"));
                    account.setBalance(0.0);
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No account", "Please create an account"));
                    account.setBalance(0.0);
                }
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No account", "Please create an account"));
        }
    }

    public void makeWithdraw(double withdrawalAmount) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                if (currentAccount != null) {
                    double currentBalance = currentAccount.getBalance();

                    if (withdrawalAmount > currentBalance) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "Your account has insufficient funds"));
                        account.setBalance(0.0);
                        return;
                    }
                    if (withdrawalAmount <= 99) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "Your can't withdraw less than 200"));
                        account.setBalance(0.0);
                        return;
                    }
                    transactionList = transactionService.getTransaction();
                    for(Transaction transaction1 : transactionList){
                        if(transaction1.getTransactionStatus().equals(TransactionStatus.PENDING) && transaction1.getAccount().getAccountId() == currentAccount.getAccountId() ){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Accepted", "You already have a withdraw pending"));
                            account.setBalance(0.0);
                            return;
                        }
                    }
                    //method call
                    transactionService.withdraw(withdrawalAmount);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Requested withdraw", "You have requested for a withdraw successfully"));
                    account.setBalance(0.0);
                }
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Account", "Please Create an account"));
                account.setBalance(0.0);
            }
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Session", "THe session expired"));
        }
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
