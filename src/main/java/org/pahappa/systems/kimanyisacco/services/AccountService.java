package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Account;

import java.util.List;

public interface AccountService {
    void createAccount(Account account);
    Account getAccountById(long accountId);
    List<Account> getAllAccounts();
    void updateAccountAmount(long accountId, double depositAmount);
    Account getAccountByMemberId(long memberId);
    void withdrawAmount(long accountId, double newAmount);
}
