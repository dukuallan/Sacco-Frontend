package org.pahappa.systems.kimanyisacco.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;

import java.util.List;

public class AccountDAO {
    public static void save(Account account){
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
    public static Account getAccountByMemberId(long memberId) {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Account WHERE member_member_id = :memberId");
            query.setParameter("memberId", memberId);
            return (Account) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateAccountAmount(long accountId, double depositAmount) {

        Session session = null;
        org.hibernate.Transaction transaction = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Account account = (Account) session.get(Account.class, accountId);
                account.setBalance(depositAmount);
                session.update(account);
                transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
        public static void withdrawAmount(long accountId, double newAmount) {
            try  {
                Session session = SessionConfiguration.getSessionFactory().openSession();
                org.hibernate.Transaction transaction = session.beginTransaction();
                Account account = (Account) session.get(Account.class, accountId);
                if (account != null) {
                    account.setBalance(newAmount);
                    session.update(account);
                    transaction.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static List<Account> getAllAccounts(){
        Session session = SessionConfiguration.getSessionFactory().openSession();

        return session.createCriteria(Account.class).list();
    }



}


