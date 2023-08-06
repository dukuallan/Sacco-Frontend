package org.pahappa.systems.kimanyisacco.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;


import java.math.BigDecimal;
import java.util.List;


public class AccountDAO {
    public void save(Account account){
        org.hibernate.Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
    public static Account getAccountById(long accountId) {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Account WHERE account_id = :accountId");
            query.setParameter("accountId", accountId);
            return (Account) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void updateAccountAmount(long accountId, double depositAmount) {
        if (depositAmount <= 0) {
            // Deposit amount should be positive
            return;
        }
        Session session = null;
        org.hibernate.Transaction transaction = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Account account = (Account) session.get(Account.class, accountId);
            if (account != null) {
                // Perform the deposit operation using BigDecimal
                BigDecimal currentAmount = BigDecimal.valueOf(account.getAmount());
                BigDecimal deposit = BigDecimal.valueOf(depositAmount);
                BigDecimal newAmount = currentAmount.add(deposit);

                account.setAmount(newAmount.doubleValue());
                session.update(account);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            // Close the session to release resources
            if (session != null) {
                session.close();
            }
        }
    }
        public void withdrawAmount(long accountId, double newAmount) {
            try  {
                Session session = SessionConfiguration.getSessionFactory().openSession();
                org.hibernate.Transaction transaction = session.beginTransaction();
                Account account = (Account) session.get(Account.class, accountId);
                if (account != null) {
                    account.setAmount(newAmount);
                    session.update(account);
                    transaction.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public static void update(Transaction transactions){
        org.hibernate.Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(transactions);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }
    public static void approveStatus(Transaction transactions){
        org.hibernate.Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(transactions);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }
    public static List<Transaction> getAllTransactions(){
        Session session = SessionConfiguration.getSessionFactory().openSession();

        return session.createCriteria(Transaction.class).list();
    }
    public static Transaction getTransactionById(long transactionId) {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Transaction WHERE transaction_id = :transactionId");
            query.setParameter("transactionId", transactionId);
            return (Transaction) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


