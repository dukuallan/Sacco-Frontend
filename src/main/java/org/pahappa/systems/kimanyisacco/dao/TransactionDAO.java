package org.pahappa.systems.kimanyisacco.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

public class TransactionDAO {

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


    public static void saveTransaction(Transaction transactions){
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
}
