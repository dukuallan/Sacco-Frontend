package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

public interface MemberService {

    void createMember(Member member);
    List<Member> getMembers();
    List<Transaction> getTransaction();

    void updateMember(Member member);

    Member getMemberById(long memberId);
    Account getAccountById(long accountId);
    Transaction getTransactionById(long transactionId);
    void updateTransactionStatus(Transaction transaction);
    void updateAccountAmount(long accountId, double depositAmount);
    void updateTransactions(Transaction transaction);
    void withdrawAmount(long accountId, double newAmount);
    void deleteMember(Member member);
    void sendVerificationEmail(String recipientEmail, String firstName);
    void sendRejectEmail(String recipientEmail ,String firstName);
    void createAccount(Account account);

}
