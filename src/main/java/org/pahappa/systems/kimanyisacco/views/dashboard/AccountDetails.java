package org.pahappa.systems.kimanyisacco.views.dashboard;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "manageAccount")
@SessionScoped
public class AccountDetails {
    private Member member;
    private List<Member> memberList;
    private MemberService memberService;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;
    Date currentDate = new Date();
    public AccountDetails() {
        this.member = new Member();
        this.account = new Account();
        this.transaction = new Transaction();
        this.memberService = new MemberServiceImpl();
    }
    public void registerAccount() throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");
            if (currentMemberId != null) {
                Member currentMember = memberService.getMemberById(currentMemberId);
                if (currentMember != null && currentMember.isStatus()) {
                    // Check if the member already has an account
                    if (currentMember.getAccount() != null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Allowed.", "You already have an account."));
                        return; // Exit the method after redirection
                    }
                    // Register account for the current member with true status
                    memberService.createAccount(account);

                    // Update the current member with account details
                    currentMember.setAccount(account);
                    memberService.updateMember(currentMember);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account Created", "You can now deposit and withdraw"));
                    session.setAttribute("account", currentMember.getAccount().getAccountId());
                    return;
                }
            }
            // If no member with status=true is found or member has no active account
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Verified", "Please wait for Verification"));
        } else {
            // Session is null, show an error message or handle it accordingly
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage errorMessage = new FacesMessage("Error: Session is not initialized.");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, errorMessage);
        }
    }
    public void makeDeposit(double depositAmount) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            System.out.println(currentAccountId);
            if (currentAccountId != null) {
                Account currentAccount = memberService.getAccountById(currentAccountId);
                System.out.println(currentAccount);
                if (currentAccount != null) {
                    double currentAmount = currentAccount.getAmount();
                    if(depositAmount<=199){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "All deposit should be greater than 199"));
                        account.setAmount(0.0);
                        return;
                    }
                    // Update the account amount with the deposit
                    System.out.println(currentAccount);
                    System.out.println(depositAmount);
                    memberService.updateAccountAmount(currentAccount.getAccountId(), depositAmount);
                    transaction.setAmount(depositAmount);
                    transaction.setType("Deposit");
                    transaction.setDate(currentDate);
                    transaction.setAccount(currentAccount);
                    transaction.setStatus(true);
                    memberService.updateTransactions(transaction);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Deposit", "Deposited Successfully"));
                    account.setAmount(0.0);
                }
            }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No account", "Please create an account"));
            account.setAmount(0.0);
        }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No account", "Please create an account"));
        }
    }
    public void makeWithdraw(double withdrawalAmount) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                Account currentAccount = memberService.getAccountById(currentAccountId);
                if (currentAccount != null) {
                    double currentBalance = currentAccount.getAmount();

                    if (withdrawalAmount > currentBalance) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "Your account has insufficient funds"));
                        account.setAmount(0.0);
                        return;
                    }
                    if (withdrawalAmount <= 99) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount", "Your can't withdraw less than 200"));
                        account.setAmount(0.0);
                        return;
                    }
                    transactionList = memberService.getTransaction();
                    for(Transaction transaction1 : transactionList){
                        if(!transaction1.isStatus() && transaction1.getAccount().getAccountId() == currentAccountId ){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Accepted", "You already have a withdraw pending"));
                            account.setAmount(0.0);
                            return;
                        }
                    }
                    transaction.setAmount(withdrawalAmount);
                    transaction.setType("Withdraw");
                    transaction.setStatus(false);
                    transaction.setWithdrawStatus("PENDING");
                    transaction.setAccount(currentAccount);
                    transaction.setDate(currentDate);
                    memberService.updateTransactions(transaction);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Requested withdraw", "You have requested for a withdraw successfully"));
                    account.setAmount(0.0);
                }
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Account", "Please Create an account"));
                account.setAmount(0.0);
            }
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Session", "THe session expired"));
        }
    }
    public List<Transaction> returnTransaction() throws Exception {
        transactionList = memberService.getTransaction();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                // Create a new list to store filtered transactions with similar account IDs
                List<Transaction> filteredTransactions = new ArrayList<>();
                // Iterate through the transactionsList to find transactions associated with the currentAccountId
                for (Transaction transaction : transactionList) {
                    if (transaction.getAccount().getAccountId() == currentAccountId && transaction.isStatus()) {
                        filteredTransactions.add(transaction);
                    }
                }
                if (!filteredTransactions.isEmpty()) {
                    return filteredTransactions;
                }
            }
        }
        return  null;
    }
    public double returnBalance(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                // method to fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
               return currentAccount.getAmount();
            }
        }
        return 0.0;
    }
    public double returnDeposit(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                //fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
                transactionList = memberService.getTransaction();
                double totalDeposit = 0.0;
                for(Transaction trans : transactionList){
                    if(trans.getType().contains("Deposit") && trans.getAccount().getAccountId() == currentAccountId ){
                        totalDeposit += trans.getAmount();
                    }
                }
                return totalDeposit;
            }
        }
        return 0.0;
    }
    public double returnWithdraw(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                //fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
                transactionList = memberService.getTransaction();
                double totalWithdraw = 0.0;
                for(Transaction trans : transactionList){
                    if(trans.getType().contains("Withdraw")  && trans.getAccount().getAccountId() == currentAccountId && trans.isStatus() && trans.getWithdrawStatus().contains("APPROVED")){
                        totalWithdraw += trans.getAmount();
                    }
                }
                return totalWithdraw;
            }
        }
        return 0.0;
    }
    public int returnDepositCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            if (currentAccountId != null) {
                // method to fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
                transactionList = memberService.getTransaction();
                int depositCount = 0;
                for (Transaction trans : transactionList) {
                    if (trans.getType().contains("Deposit") && trans.getAccount().getAccountId() == currentAccountId) {
                        depositCount++;
                    }
                }
                return depositCount;
            }
        }
        return 0;
    }
    public int returnWithdrawCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            System.out.println(currentAccountId);
            if (currentAccountId != null) {
                // to fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
                System.out.println(currentAccount);
                transactionList = memberService.getTransaction();
                int withdrawCount = 0;
                for (Transaction trans : transactionList) {
                    if (trans.getType().contains("Withdraw") && trans.getAccount().getAccountId() == currentAccountId && trans.isStatus() && trans.getWithdrawStatus().contains("APPROVED")) {
                        withdrawCount++;
                    }
                }
                return withdrawCount;
            }
        }
        return 0;
    }
    public int returnNotificationCount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            Long currentAccountId = (Long) session.getAttribute("account");
            System.out.println(currentAccountId);
            if (currentAccountId != null) {
                // to fetch the current account based on the account ID
                Account currentAccount = memberService.getAccountById(currentAccountId);
                System.out.println(currentAccount);
                transactionList = memberService.getTransaction();
                int withdrawCount = 0;
                for (Transaction trans : transactionList) {
                    if (trans.getType().contains("Withdraw") && trans.getAccount().getAccountId() == currentAccountId && trans.isStatus()) {
                        withdrawCount++;
                    }
                }
                return withdrawCount;
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
    public List<Member> getMemberList() {
        return memberList;
    }
    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public Transaction getTransactions() {
        return transaction;
    }
    public void setTransactions(Transaction transaction) {
        this.transaction = transaction;
    }
    public List<Transaction> getTransactionsList() {
        return transactionList;
    }
    public void setTransactionsList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
