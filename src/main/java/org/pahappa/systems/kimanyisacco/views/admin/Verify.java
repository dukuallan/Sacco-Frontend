package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "verify")
@SessionScoped
public class Verify {
    private Member member;
    private MemberService memberService;
    private List<Member> members;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private Account account;

    Date currentDate = new Date();
    public Verify(){
        this.member = new Member();
        this.memberService = new MemberServiceImpl();
        this.account = new Account();
        this.transaction = new Transaction();
    }
    public List<Member> showMembers(){
        members=memberService.getMembers();
        List<Member> filteredMembers = new ArrayList<>();
        for(Member member: members){
            if(!member.isStatus()){
              filteredMembers.add(member);
            }
        }
        return filteredMembers;
    }
    public int returnTotalVerifiedMembers(){
        members = memberService.getMembers();
        int totalMembers = 0;
        for(Member member1 : members){
            if(member1.isStatus()){
                totalMembers++;
            }
        }
        return  totalMembers;
    }
    public int returnTotalUnverifiedMembers(){
        members = memberService.getMembers();
        int totalMembers = 0;
        for(Member member1 : members){
            if(!member1.isStatus()){
                totalMembers++;
            }
        }
        return  totalMembers;
    }
    public void updateMember(Member member) {
        member.setStatus(true);
        memberService.updateMember(member);
        memberService.sendVerificationEmail(member.getEmail(), member.getFirstName());
    }
    public List<Transaction> returnApprovalTransaction() {
        transactionList = memberService.getTransaction();
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (!transaction.isStatus()) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    public void approveWithdrawal(long transactionId) {
        Transaction transaction = memberService.getTransactionById(transactionId);
        System.out.println(transaction);

        if (transaction != null) {
            if ("Withdraw".equals(transaction.getType()) && !transaction.isStatus()) {
                Account account = memberService.getAccountById(transaction.getAccount().getAccountId());
                if (account != null) {
                    // Update the account balance by subtracting the withdrawal amount
                    double currentBalance = account.getAmount();
                    double withdrawalAmount = transaction.getAmount();
                    double newBalance = currentBalance - withdrawalAmount;
                    memberService.withdrawAmount(account.getAccountId(),newBalance);

                    // Update the transaction status to approved (status is true)
                    transaction.setStatus(true);
                    transaction.setWithdrawStatus("APPROVED");
                    transaction.setDate(currentDate);
                    transaction.setNotification("Your withdraw of " + withdrawalAmount +" was accepted");
                    memberService.updateTransactionStatus(transaction);

                }
            }
        }
    }
    public List<Transaction> returnApprovalWithdraw() {
        transactionList = memberService.getTransaction();
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.isStatus() && transaction.getType().contains("Withdraw")) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    public void rejectWithdrawal(long transactionId) {
        Transaction transaction = memberService.getTransactionById(transactionId);
        System.out.println(transaction);

        if (transaction != null) {
            if ("Withdraw".equals(transaction.getType()) && !transaction.isStatus()) {
                // Update the transaction status to approved (status is true)
                double withdrawalAmount = transaction.getAmount();
                transaction.setStatus(true);
                transaction.setWithdrawStatus("REJECTED");
                transaction.setDate(currentDate);
                transaction.setNotification("Your withdraw of " + withdrawalAmount + " was rejected");
                memberService.updateTransactionStatus(transaction);

            }
        }
    }
    public double returnTotalDeposit(){
        transactionList = memberService.getTransaction();
        double totalDeposit = 0.0;
        for(Transaction trans : transactionList){
            if(trans.getType().contains("Deposit")){
                totalDeposit += trans.getAmount();
            }
        }
        return totalDeposit;
    }
    public double returnTotalWithdraw(){
        transactionList = memberService.getTransaction();
        double totalWithdraw = 0.0;
        for(Transaction trans : transactionList){
            if(trans.getType().contains("Withdraw")){
                totalWithdraw += trans.getAmount();
            }
        }
        return totalWithdraw;
    }
    public int returnTotalDepositCount() {
        transactionList = memberService.getTransaction();
        int depositCount = 0;
        for (Transaction trans : transactionList) {
            if (trans.getType().contains("Deposit")) {
                depositCount++;
            }
        }
        return depositCount;
    }
    public int returnTotalWithdrawCount() {
        transactionList = memberService.getTransaction();
        int withdrawCount = 0;
        for (Transaction trans : transactionList) {
            if (trans.getType().contains("Withdraw")) {
                withdrawCount++;
            }
        }
        return withdrawCount;

    }
    public void deleteMember(Member member){
        memberService.sendRejectEmail(member.getEmail(), member.getFirstName());
        memberService.deleteMember(member);
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public MemberService getMemberService() {
        return memberService;
    }
    public List<Member> getMembers() {
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
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
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
