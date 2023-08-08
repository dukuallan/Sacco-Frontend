package org.pahappa.systems.kimanyisacco.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Member;

import java.util.List;

public class MemberDAO {
    public static void save(Member member){
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
    public static List<Member> getAllMembers(){
        Session session = SessionConfiguration.getSessionFactory().openSession();

        return session.createCriteria(Member.class).list();
    }
    public static Member getMemberById(long memberId) {
        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Member WHERE member_id = :memberId");
            query.setParameter("memberId", memberId);
            return (Member) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void update(Member member){
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(member);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }

    public static void remove(Member member){
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(member);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }

}
